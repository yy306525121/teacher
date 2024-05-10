package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.*;
import cn.codeyang.course.dto.coursefee.IgnoreItemDto;
import cn.codeyang.course.dto.coursefee.PreDeleteItemDto;
import cn.codeyang.course.enums.CourseTypeEnum;
import cn.codeyang.course.enums.TimeSlotTypeEnum;
import cn.codeyang.course.service.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 放假规则计算
 */
@Order(60)
@Component
@RequiredArgsConstructor
public class ExamRuleCalculate implements CourseFeeCalculate {
    private final ExamRuleService examRuleService;
    private final ClassInfoService classInfoService;
    private final TimeSlotService timeSlotService;
    private final TeacherService teacherService;
    private final CoursePlanService coursePlanService;

    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        List<ExamRule> list = examRuleService.getListByDate(startDate, endDate);

        // 获取放假的日期和节次
        List<IgnoreItemDto> ignoreItemList = getIgnoreItem(startDate, endDate, list);

        // 因为跨阶段无法立即删除课时的教师，先存入这里，后续处理
        List<PreDeleteItemDto> preMorningDeleteItemList = new ArrayList<>();

        for (IgnoreItemDto ignoreItem : ignoreItemList) {
            if (CollUtil.isEmpty(courseFeeList)) {
                continue;
            }
            if (!ignoreItem.getTimeSlot().getType().equals(TimeSlotTypeEnum.MORNING.getType())) {
                // 如果不是早自习,直接删除
                List<ClassInfo> classInfoList = classInfoService.selectListByParentId(ignoreItem.getClassInfo().getId());
                List<Long> classInfoIdList = classInfoList.stream().map(ClassInfo::getId).toList();
                courseFeeList.removeIf(
                        fee ->
                                fee.getDate().equals(ignoreItem.getDate()) &&
                                        fee.getClassInfoId() != null &&
                                        classInfoIdList.contains(fee.getClassInfoId()) &&
                                        fee.getTimeSlotId().equals(ignoreItem.getTimeSlot().getId()));
            } else {
                // 如果是早自习，换一种删除方法， 因为早自习的courseFee的班级ID为空
                int week = ignoreItem.getDate().getDayOfWeek().getValue();
                List<CoursePlan> coursePlanList = coursePlanService.selectListByWeekAndCourseType(ignoreItem.getDate(), week, CourseTypeEnum.MORNING.getType());
                List<Long> subjectIdList = coursePlanList.stream().map(CoursePlan::getSubjectId).distinct().toList();
                List<TimeSlot> timeSlots = timeSlotService.selectListByType(TimeSlotTypeEnum.MORNING.getType());
                List<Long> morningTimeSlotIdList = timeSlots.stream().map(TimeSlot::getId).toList();
                for (Long subjectId : subjectIdList) {
                    // 1.查询只任课某阶段的早自习科目的老师， 然后删除该老师的早自习课时
                    List<Teacher> teacherList = teacherService.getListByTopClassInfoIdAndSubjectIdOnly(ignoreItem.getClassInfo().getId(), subjectId);
                    List<Long> teacherIdList = teacherList.stream().map(Teacher::getId).toList();
                    courseFeeList.removeIf(
                            fee ->
                                    fee.getDate().equals(ignoreItem.getDate()) &&
                                            morningTimeSlotIdList.contains(fee.getTimeSlotId()) &&
                                            teacherIdList.contains(fee.getTeacherId())
                    );

                    // 2. 查询跨阶段教师的早自习科目老师，留待后续判断是否需要删除
                    List<Teacher> manyClassTeacherList = teacherService.getGt1ClassListByTopClassAndSubjectId(ignoreItem.getClassInfo().getId(), subjectId);
                    if (!manyClassTeacherList.isEmpty()) {
                        PreDeleteItemDto preDeleteItemDto = new PreDeleteItemDto();
                        preDeleteItemDto.setDate(ignoreItem.getDate());
                        preDeleteItemDto.setWeek(week);
                        preDeleteItemDto.setTimeSlotIdList(morningTimeSlotIdList);
                        preDeleteItemDto.setSubjectId(subjectId);
                        preDeleteItemDto.setTeacherList(manyClassTeacherList);
                        preMorningDeleteItemList.add(preDeleteItemDto);
                    }
                }
            }
        }

        if (!preMorningDeleteItemList.isEmpty()) {
            // 处理因为跨阶段而无法删除课时的老师的课时费
            preMorningDeleteItemList.forEach( preDeleteItem -> {
                for (Teacher teacher : preDeleteItem.getTeacherList()) {
                    // 查询该教师任课哪些年级
                    List<ClassInfo> classInfoList = classInfoService.selectTopListBySubjectIdAndTeacherId(preDeleteItem.getSubjectId(), teacher.getId());
                    boolean flag = true;
                    for (ClassInfo classInfo : classInfoList) {
                        long count = ignoreItemList.stream().filter(item ->
                                        item.getClassInfo().getId().equals(classInfo.getId()) &&
                                                item.getDate().equals(preDeleteItem.getDate()) &&
                                                preDeleteItem.getTimeSlotIdList().contains(item.getTimeSlot().getId()))
                                .count();
                        if (count == 0) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        courseFeeList.removeIf(
                                fee ->
                                        fee.getDate().equals(preDeleteItem.getDate()) &&
                                                preDeleteItem.getTimeSlotIdList().contains(fee.getTimeSlotId()) &&
                                                teacher.getId().equals(fee.getTeacherId())
                        );
                    }

                }
            });
        }

        return courseFeeList;
    }

    @SneakyThrows
    private List<IgnoreItemDto> getIgnoreItem(LocalDate startDate, LocalDate endDate, List<ExamRule> ruleList) {
        List<IgnoreItemDto> ignoreCoursePlanList = new ArrayList<>();

        List<ClassInfo> classInfoLevel2List = classInfoService.listByParentIdList(List.of(0L));
        TimeSlot firstTimeSlot = timeSlotService.getFirst();
        TimeSlot lastTimeSlot = timeSlotService.getLast();

        for (ExamRule rule : ruleList) {
            if (rule.getStartDate() == null && rule.getEndDate() == null) {
                throw new Exception("放假规则: " + rule.getId() + "有误");
            }

            // 1. 规则适用班级
            List<ClassInfo> ruleClassInfoList = classInfoLevel2List;
            if (StrUtil.isNotEmpty(rule.getClassInfoId())) {
                List<Long> ruleClassInfoIdList = JSONUtil.toList(rule.getClassInfoId(), Long.class);
                if (CollUtil.isNotEmpty(ruleClassInfoIdList)) {
                    ruleClassInfoList = classInfoService.listByIds(ruleClassInfoIdList);
                }
            }

            // 2. 生成需要排除的课程
            //// 2.1. 规则开始日期
            LocalDate ruleStartDate = rule.getStartDate();
            //// 2.2. 规则开始节次
            TimeSlot ruleStartTimeSlot = timeSlotService.getById(rule.getStartTimeSlotId());
            //// 2.3. 规则结束日期
            LocalDate ruleEndDate = rule.getEndDate();
            //// 2.4. 规则结束节次
            TimeSlot ruleEndTimeSlot = timeSlotService.getById(rule.getEndTimeSlotId());

            if (ruleStartDate.isBefore(startDate)) {
                // 如果规则时间早于计算开始时间， 生成规则从计算开始时间开始算， 主要是为了缩小生成的排除课程列表的数量
                ruleStartDate = startDate;
                ruleStartTimeSlot = firstTimeSlot;
            }
            if (ruleEndDate.isAfter(endDate)) {
                ruleEndDate = endDate;
                ruleEndTimeSlot = lastTimeSlot;
            }

            LocalDate tmpDate = ruleStartDate;
            while (!tmpDate.isAfter(ruleEndDate)) {
                for (int i = firstTimeSlot.getSortInDay(); i <= lastTimeSlot.getSortInDay(); i++) {
                    if (tmpDate.equals(ruleStartDate) && i < ruleStartTimeSlot.getSortInDay()) {
                        continue;
                    }
                    if (tmpDate.equals(ruleEndDate) && i > ruleEndTimeSlot.getSortInDay()) {
                        continue;
                    }

                    for (ClassInfo classInfo : ruleClassInfoList) {
                        TimeSlot timeSlot = timeSlotService.getBySortInDay(i);
                        IgnoreItemDto ignoreItem = new IgnoreItemDto();
                        ignoreItem.setDate(tmpDate);
                        ignoreItem.setTimeSlot(timeSlot);
                        ignoreItem.setClassInfo(classInfo);
                        ignoreCoursePlanList.add(ignoreItem);
                    }
                }
                tmpDate = tmpDate.plusDays(1);
            }

        }

        return ignoreCoursePlanList;
    }
}
