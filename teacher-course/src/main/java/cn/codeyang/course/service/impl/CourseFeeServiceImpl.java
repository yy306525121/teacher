package cn.codeyang.course.service.impl;

import cn.codeyang.course.constant.CourseConstant;
import cn.codeyang.course.domain.*;
import cn.codeyang.course.dto.coursefee.CourseFeeDetailRspDto;
import cn.codeyang.course.dto.coursefee.CourseFeeExportRspDTO;
import cn.codeyang.course.dto.coursefee.CourseFeePageRspDto;
import cn.codeyang.course.dto.coursefee.IgnoreItemDto;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageResponse;
import cn.codeyang.course.mapper.CourseFeeMapper;
import cn.codeyang.course.service.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseFeeServiceImpl extends ServiceImpl<CourseFeeMapper, CourseFee> implements CourseFeeService {
    private final CoursePlanService coursePlanService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final CourseFeeRuleService courseFeeRuleService;
    private final TimeSlotService timeSlotService;
    private final ClassInfoService classInfoService;
    private final CourseTypeService courseTypeService;



    @Override
    public IPage<CourseFeePageRspDto> selectPageList(Page<CourseFeePageRspDto> page, LocalDate start, LocalDate end) {
        return baseMapper.selectPageList(page, start, end);
    }

    @Override
    public void calculate(Long teacherId, LocalDate start, LocalDate end) {
        List<CourseType> allCourseType = courseTypeService.selectAll();

        // 1. 清除原有数据
        this.baseMapper.delete(Wrappers.<CourseFee>lambdaQuery().ge(CourseFee::getDate, start).le(CourseFee::getDate, end).eq(teacherId != null, CourseFee::getTeacherId, teacherId));

        List<CourseFeeRulePageResponse> feeRuleList = courseFeeRuleService.selectFeeRuleList(start);
        List<IgnoreItemDto> ignoreItemList = new ArrayList<>(0);
        if (CollUtil.isNotEmpty(feeRuleList)) {
            ignoreItemList = getIgnoreItem(feeRuleList);
        }

        // 所有的调课规则
        List<CourseFeeRule> changeFeeRuleList = courseFeeRuleService.selectChangeTypeList(start);

        // 2.开始计算课时费
        LocalDate current = start;
        List<CourseFee> courseFeeList = new ArrayList<>();
        while (!current.isAfter(end)) {
            courseFeeList.addAll(calculate(teacherId, current, ignoreItemList));
            // 下一天
            current = current.plus(1, ChronoUnit.DAYS);
        }

        // 处理调课的课时
        changeFeeRuleList.forEach(rule -> {
            List<CourseFee> filterCourseFee = courseFeeList
                    .stream()
                    .filter(courseFee ->
                            courseFee.getDate().equals(rule.getOverrideDate()) &&
                                    courseFee.getTimeSlotId().equals(rule.getOverrideTimeSlotId()) &&
                                    courseFee.getTeacherId().equals(rule.getOverrideFromTeacherId()))
                    .collect(Collectors.toList());
            if (filterCourseFee.size() > 1) {
                throw new RuntimeException("课时费计算有误");
            }
            filterCourseFee.forEach(courseFee -> {
                courseFee.setTeacherId(rule.getOverrideToTeacherId());
                allCourseType
                        .stream()
                        .filter(courseType -> courseType.getId().equals(rule.getOverrideToCourseTypeId())).findFirst()
                        .ifPresent(filterCourseType -> courseFee.setCount(filterCourseType.getPrice()));
            });
        });

        this.saveBatch(courseFeeList);
    }

    @Override
    public List<CourseFeeDetailRspDto> selectListGroupByDate(Long teacherId, LocalDate start, LocalDate end) {
        return baseMapper.selectListGroupByDate(teacherId, start, end);
    }

    @Override
    public List<CourseFeeExportRspDTO> selectExportList(LocalDate start, LocalDate end) {
        return baseMapper.selectExportList(start, end);
    }


    /**
     * 根据feeRule组装需要过滤的日期和课程课程节次ID
     * @param feeRuleList
     */
    private List<IgnoreItemDto> getIgnoreItem(List<CourseFeeRulePageResponse> feeRuleList) {
        List<IgnoreItemDto> ignoreCoursePlanList = new ArrayList<>();

        if (CollUtil.isNotEmpty(feeRuleList)) {
            //开始日期为空，默认开始时间为为规则所在月的第一天的第一节课
            TimeSlot firstTimeSlot = timeSlotService.getFirst();
            TimeSlot lastTimeSlot = timeSlotService.getLast();

            for (CourseFeeRulePageResponse feeRule : feeRuleList) {
                if (feeRule.getStartDate() == null && feeRule.getEndDate() == null) {
                    throw new RuntimeException("规则: " + feeRule.getId() + "有误");
                }
                if (feeRule.getStartDate() == null) {
                    feeRule.setStartDate(feeRule.getEndDate().with(TemporalAdjusters.firstDayOfMonth()));
                    feeRule.setStartTimeSlotId(firstTimeSlot.getId());
                    feeRule.setStartSortInDay(firstTimeSlot.getSortInDay());
                } else if (feeRule.getEndDate() == null) {
                    feeRule.setEndDate(feeRule.getStartDate().with(TemporalAdjusters.lastDayOfMonth()));
                    feeRule.setEndTimeSlotId(lastTimeSlot.getId());
                    feeRule.setEndSortInDay(lastTimeSlot.getSortInDay());
                }

                // 获取feeRule中的班级列表
                List<ClassInfo> classInfoList = classInfoService.listByParentIdList(List.of(0L));
                if (StrUtil.isNotEmpty(feeRule.getClassInfoId())) {
                    List<Long> classInfoIdList = JSONUtil.toList(feeRule.getClassInfoId(), Long.class);
                    if (CollUtil.isNotEmpty(classInfoIdList)) {
                        classInfoList = classInfoService.listByIds(classInfoIdList);
                    }
                }
                // 生成需要排除的课程
                LocalDate currentDate = feeRule.getStartDate();
                while (!currentDate.isAfter(feeRule.getEndDate())) {
                    for (int i = firstTimeSlot.getSortInDay(); i <= lastTimeSlot.getSortInDay(); i++) {
                        if (currentDate.equals(feeRule.getStartDate()) && i < feeRule.getStartSortInDay()) {
                            continue;
                        }
                        if (currentDate.equals(feeRule.getEndDate()) && i > feeRule.getEndSortInDay()) {
                            continue;
                        }

                        for (ClassInfo classInfo : classInfoList) {
                            TimeSlot timeSlot = timeSlotService.getBySortInDay(i);
                            IgnoreItemDto ignoreItem = new IgnoreItemDto();
                            ignoreItem.setDate(currentDate);
                            ignoreItem.setTimeSlotId(timeSlot.getId());
                            ignoreItem.setClassInfo(classInfo);
                            ignoreCoursePlanList.add(ignoreItem);
                        }
                    }
                    currentDate = currentDate.plusDays(1);
                }
            }
        }
        return ignoreCoursePlanList;
    }

    /**
     * 计算指定日期的课时
     *
     * @param date
     */
    private List<CourseFee> calculate(Long teacherId, LocalDate date, List<IgnoreItemDto> ignoreItemList) {


        int week = date.getDayOfWeek().getValue();
        // 1. 查询指定周下的所有课程计划
        List<CoursePlanDto> coursePlans = coursePlanService.selectListByWeekAndTeacherId(week, teacherId, null);

        List<CourseFee> courseFeeList = new ArrayList<>(coursePlans.size());


        long count = coursePlans.stream().filter(coursePlan -> coursePlan.getCourseType().getType() == 3).count();
        if (count > 0) {
            // 如果存在早自习课程（早自习课时需要单独计算），除了周日，其他日期都有早自习
            courseFeeList.addAll(calculateMorningEarly(date, week, ignoreItemList));
        }

        for (CoursePlanDto coursePlan : coursePlans) {
            if (coursePlan.getTeacher() == null) {
                continue;
            }

            ClassInfo classInfo = classInfoService.getById(coursePlan.getClassInfo().getParentId());
            if (ignoreItemList.stream().anyMatch(item ->
                    item.getDate().equals(date) &&
                            item.getTimeSlotId().equals(coursePlan.getTimeSlot().getId()) &&
                            item.getClassInfo().getId().equals(classInfo.getId()))) {
                continue;
            }

            CourseFee courseFee = new CourseFee();
            courseFee.setCount(coursePlan.getCourseType().getPrice());
            courseFee.setDate(date);
            courseFee.setTeacherId(coursePlan.getTeacher().getId());
            courseFee.setClassInfoId(coursePlan.getClassInfo().getId());
            if (coursePlan.getSubject() != null) {
                // 白天自习课无课程
                courseFee.setSubjectId(coursePlan.getSubject().getId());
            }
            courseFee.setWeek(coursePlan.getDayOfWeek());
            courseFee.setTimeSlotId(coursePlan.getTimeSlot().getId());
            courseFeeList.add(courseFee);
        }

        return courseFeeList;
    }

    private List<ClassInfo> getLevel2ClassIdListNotInIgnore(List<IgnoreItemDto> ignoreItemList) {
        List<ClassInfo> list = new ArrayList<>(0);
        if (CollUtil.isEmpty(ignoreItemList)) {
            // 如果不需要排除， 查询所有的二级班级
            return classInfoService.listLevel2ByNotIn(null);
        }

        // 这里的班级都是一级班级的ID
        List<Long> ignoreLevel1ClassInfoIdList = ignoreItemList.stream().map(IgnoreItemDto::getClassInfo).map(ClassInfo::getId).collect(Collectors.toList());
        // 获取所有的二级ignore班级
        List<ClassInfo> ignoreLevel2ClassInfoList = classInfoService.listByParentIdList(ignoreLevel1ClassInfoIdList);
        List<Long> ignoreLevel2ClassInfoIdList = ignoreLevel2ClassInfoList.stream().map(ClassInfo::getId).collect(Collectors.toList());

        // 查询所有的不需要排除的二级班级
        return classInfoService.listLevel2ByNotIn(ignoreLevel2ClassInfoIdList);
    }

    // 计算早自习课时
    private List<CourseFee> calculateMorningEarly(LocalDate date, int week, List<IgnoreItemDto> ignoreItemList) {
        // 查询早自习的timeSlot
        List<TimeSlot> timeSlotMorning = timeSlotService.getByType(CourseConstant.TIME_SLOT_TYPE_MORNING);

        List<CourseFee> courseFeeList = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlotMorning) {
            List<IgnoreItemDto> matchIgnoreItemList = ignoreItemList.stream().filter(item -> item.getDate().equals(date) && item.getTimeSlotId().equals(timeSlot.getId())).collect(Collectors.toList());
            List<ClassInfo> classInfoList = getLevel2ClassIdListNotInIgnore(matchIgnoreItemList);
            if (CollUtil.isEmpty(classInfoList)) {
                continue;
            }
            List<Long> classInfoIdList = classInfoList.stream().map(ClassInfo::getId).collect(Collectors.toList());


            List<Teacher> teacherList = new ArrayList<>();
            Subject subject = null;
            if (CourseConstant.chineseMorningEarlyWeek.contains(week)) {
                //语文早自习
                subject = subjectService.getByName(CourseConstant.SUBJECT_CHINESE);
                teacherList = teacherService.selectListBySubjectName(CourseConstant.SUBJECT_CHINESE, classInfoIdList);
            } else if (CourseConstant.englishMorningEarlyWeek.contains(week)) {
                // 英语早自习
                subject = subjectService.getByName(CourseConstant.SUBJECT_ENGLISH);
                teacherList = teacherService.selectListBySubjectName(CourseConstant.SUBJECT_ENGLISH, classInfoIdList);
            }

            for (Teacher teacher : teacherList) {
                CourseFee courseFee = new CourseFee();
                courseFee.setCount(CourseConstant.MORNING_EARLY_FEE);
                courseFee.setTeacherId(teacher.getId());
                if (subject != null) {
                    courseFee.setSubjectId(subject.getId());
                }
                courseFee.setWeek(week);
                courseFee.setTimeSlotId(timeSlot.getId());
                courseFee.setDate(date);
                courseFeeList.add(courseFee);
            }
        }
        return courseFeeList;
    }
}
