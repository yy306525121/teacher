package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.*;
import cn.codeyang.course.enums.CourseTypeEnum;
import cn.codeyang.course.service.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Order(41)
@Component
@RequiredArgsConstructor
public class FillRuleCalculate implements CourseFeeCalculate {
    private final FillRuleService fillRuleService;
    private final CoursePlanService coursePlanService;
    private final ClassInfoService classInfoService;
    private final TimeSlotService timeSlotService;
    private final TeacherService teacherService;
    private final CourseTypeService courseTypeService;

    /**
     * 早自习课时费
     */
    private BigDecimal morningPrice;
    private BigDecimal normalPrice;
    private BigDecimal studySelfPrice;
    private BigDecimal eveningPrice;

    @SneakyThrows
    @PostConstruct
    public void init() {
        CourseType morningCourseType = courseTypeService.selectByType(CourseTypeEnum.MORNING.getType());
        CourseType normalCourseType = courseTypeService.selectByType(CourseTypeEnum.NORMAL.getType());
        CourseType studySelfCourseType = courseTypeService.selectByType(CourseTypeEnum.STUDY_SELF.getType());
        CourseType eveningCourseType = courseTypeService.selectByType(CourseTypeEnum.EVENING.getType());
        if (morningCourseType == null || normalCourseType == null || studySelfCourseType == null || eveningCourseType == null) {
            throw new Exception("请检查t_course_type表中课程类型是否完整");
        }
        morningPrice = morningCourseType.getPrice();
        normalPrice = normalCourseType.getPrice();
        studySelfPrice = studySelfCourseType.getPrice();
        eveningPrice = eveningCourseType.getPrice();
    }

    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        List<FillRule> rules = fillRuleService.getListByDate(startDate, endDate);
        List<TimeSlot> timeSlotList = timeSlotService.list();

        TimeSlot firstTimeSlot = timeSlotService.getFirst();
        TimeSlot lastTimeSlot = timeSlotService.getLast();

        for (FillRule rule : rules) {
            int deleteFrom = firstTimeSlot.getSortInDay();
            int deleteTo = lastTimeSlot.getSortInDay();

            // 删除原有的补课日的晚自习，
            Long startTimeSlotId = rule.getStartTimeSlotId();
            if (startTimeSlotId != null) {
                TimeSlot startTimeSlot = timeSlotList.stream().filter(timeSlot -> timeSlot.getId().equals(startTimeSlotId)).findFirst().orElseThrow();
                deleteFrom = startTimeSlot.getSortInDay();
            }
            Long endTimeSlotId = rule.getEndTimeSlotId();
            if (endTimeSlotId != null) {
                TimeSlot endTimeSlot = timeSlotList.stream().filter(timeSlot -> timeSlot.getId().equals(endTimeSlotId)).findFirst().orElseThrow();
                deleteTo = endTimeSlot.getSortInDay();
            }

            if (CollUtil.isNotEmpty(courseFeeList)) {
                int finalDeleteFrom = deleteFrom;
                int finalDeleteTo = deleteTo;

                List<Long> deleteClassIdList = new ArrayList<>();
                if (StrUtil.isNotEmpty(rule.getClassInfoId())) {
                    List<Long> classInfoIdList = JSONUtil.toList(rule.getClassInfoId(), Long.class);
                    if (CollUtil.isNotEmpty(classInfoIdList)) {
                        List<ClassInfo> classInfos = classInfoService.listByParentIdList(classInfoIdList);
                        deleteClassIdList = classInfos.stream().map(ClassInfo::getId).toList();
                    }

                }
                List<Long> finalDeleteClassIdList = deleteClassIdList;
                courseFeeList.removeIf(courseFee -> {
                    TimeSlot courseFeeTimeSlot = timeSlotList.stream().filter(timeSlot -> timeSlot.getId().equals(courseFee.getTimeSlotId())).findFirst().orElseThrow();
                    if (CollUtil.isNotEmpty(finalDeleteClassIdList)) {
                        return courseFee.getDate().equals(rule.getDate()) &&
                                courseFeeTimeSlot.getSortInDay() >= finalDeleteFrom &&
                                courseFeeTimeSlot.getSortInDay() <= finalDeleteTo &&
                                finalDeleteClassIdList.contains(courseFee.getClassInfoId());
                    } else {
                        return courseFee.getDate().equals(rule.getDate()) &&
                                courseFeeTimeSlot.getSortInDay() >= finalDeleteFrom &&
                                courseFeeTimeSlot.getSortInDay() <= finalDeleteTo;
                    }
                });
            }


            courseFeeList.addAll(calculateRule(rule, timeSlotList));
        }

        return courseFeeList;
    }

    private List<CourseFee> calculateRule(FillRule rule, List<TimeSlot> timeSlotList) {
        // 计算全天补课课时费
        Integer week = rule.getWeek();
        LocalDate date = rule.getDate();
        List<CoursePlan> coursePlanList = coursePlanService.selectListByWeekAndCourseType(date, week, CourseTypeEnum.MORNING.getType());
        // 计算早自习课时
        List<CourseFee> courseFeeList = calculateMorningFee(teacherService, coursePlanList, morningPrice, week, date);
        // 计算正常课时
        courseFeeList.addAll(calculateNormalFee(coursePlanService, date, week, normalPrice));
        // 计算自习课时
        courseFeeList.addAll(calculateStudySelf(coursePlanService, date, week, studySelfPrice));
        // 计算晚自习课时
        courseFeeList.addAll(calculateEvening(coursePlanService, date, week, eveningPrice));

        if (StrUtil.isNotEmpty(rule.getClassInfoId())) {
            // 获取需要保留下来的教师的courseFee
            List<Long> teacherIds = new ArrayList<>();
            JSONUtil.toList(rule.getClassInfoId(), Long.class).forEach(id -> {
                List<ClassInfo> classInfoList = classInfoService.selectListByParentId(id);
                List<Long> classInfoIdList = classInfoList.stream().map(ClassInfo::getId).toList();
                // 查询这些班级的所有任课老师
                List<CoursePlan> classCoursePlanList = coursePlanService.selectListByClassInfoIdsAndDate(classInfoIdList, date);
                List<Long> classTeacherList = classCoursePlanList.stream().map(CoursePlan::getTeacherId).filter(Objects::nonNull).toList();
                teacherIds.addAll(classTeacherList);
            });
            if (CollUtil.isNotEmpty(courseFeeList)) {
                courseFeeList.removeIf(item -> !teacherIds.contains(item.getTeacherId()));
            }
        }

        if (rule.getStartTimeSlotId() != null) {
            TimeSlot startTimeSlot = timeSlotService.getById(rule.getStartTimeSlotId());
            if (CollUtil.isNotEmpty(courseFeeList)) {
                courseFeeList.removeIf(item -> {
                    Long timeSlotId = item.getTimeSlotId();
                    TimeSlot itemTimeSlot = timeSlotList.stream().filter(timeSlot -> timeSlot.getId().equals(timeSlotId)).findFirst().orElse(null);
                    if (itemTimeSlot == null) {
                        return true;
                    }
                    if (itemTimeSlot.getSortInDay() < startTimeSlot.getSortInDay()) {
                        return true;
                    }
                    return false;
                });
            }
        }
        if (rule.getStartTimeSlotId() != null) {
            TimeSlot endTimeSlot = timeSlotService.getById(rule.getEndTimeSlotId());
            if (CollUtil.isNotEmpty(courseFeeList)) {
                courseFeeList.removeIf(item -> {
                    Long timeSlotId = item.getTimeSlotId();
                    TimeSlot itemTimeSlot = timeSlotList.stream().filter(timeSlot -> timeSlot.getId().equals(timeSlotId)).findFirst().orElse(null);
                    if (itemTimeSlot == null) {
                        return true;
                    }
                    if (itemTimeSlot.getSortInDay() > endTimeSlot.getSortInDay()) {
                        return true;
                    }
                    return false;
                });
            }
        }


        return courseFeeList;
    }
}
