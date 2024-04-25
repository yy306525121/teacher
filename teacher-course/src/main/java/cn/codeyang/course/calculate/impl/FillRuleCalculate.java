package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.FillRule;
import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.enums.CourseTypeEnum;
import cn.codeyang.course.service.*;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Order(41)
@Component
@RequiredArgsConstructor
public class FillRuleCalculate implements CourseFeeCalculate {
    private final FillRuleService fillRuleService;
    private final CoursePlanService coursePlanService;
    private final ClassInfoService classInfoService;
    private final TimeSlotService timeSlotService;
    private final TeacherService teacherService;

    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        List<FillRule> rules = fillRuleService.getListByDate(startDate, endDate);


        for (FillRule rule : rules) {
            // 1 获取补课周
            Integer week = rule.getWeek();
            // 2 获取补课日期
            LocalDate date = rule.getDate();
            // 3 获取所有的补课班级
            List<ClassInfo> classInfoList = getRuleLevel2ClassList(rule);
            for (ClassInfo classInfo : classInfoList) {
                List<CoursePlanDto> coursePlanList = coursePlanService.selectListByDateAndWeekAndClassInfoId(date, week, classInfo.getId());
                if (rule.getStartTimeSlotId() != null) {
                    TimeSlot startTimeSlot = timeSlotService.getById(rule.getStartTimeSlotId());
                    coursePlanList = coursePlanList.stream().filter(item -> item.getTimeSlot().getSortInDay() >= startTimeSlot.getSortInDay()).toList();
                }
                if (rule.getEndTimeSlotId() != null) {
                    TimeSlot endTimeSlot = timeSlotService.getById(rule.getEndTimeSlotId());
                    coursePlanList = coursePlanList.stream().filter(item -> item.getTimeSlot().getSortInDay() <= endTimeSlot.getSortInDay()).toList();
                }

                // 开始计算课时
                for (CoursePlanDto coursePlan : coursePlanList) {
                    if (CourseTypeEnum.MORNING.getType().equals(coursePlan.getCourseType().getType())) {
                        // 如果是早自习，获取该课程、该年级的所有老师
                    }
                }
            }
        }

        return List.of();
    }

    private List<CourseFee> calculateRule(FillRule rule) {
        List<CourseFee> courseFeeList = new ArrayList<>();

        if (rule.getStartTimeSlotId() == null && rule.getEndTimeSlotId() == null && rule.getClassInfoId() == null) {

        }

        return courseFeeList;
    }

    private List<ClassInfo> getRuleLevel2ClassList(FillRule rule) {
        List<ClassInfo> classInfoList = new ArrayList<>();
        String classInfoIdJsonStr = rule.getClassInfoId();
        if (StrUtil.isEmpty(classInfoIdJsonStr)) {
            // 如果为空说明所有的班级都调课
            classInfoList = classInfoService.listAllLevel2();
        } else {
            List<Long> topClassInfoIdList = JSONUtil.toList(classInfoIdJsonStr, Long.class);
            for (Long topClassInfoId : topClassInfoIdList) {
                classInfoList.addAll(classInfoService.listByParentIdList(ListUtil.toList(topClassInfoId)));
            }
        }
        return classInfoList;
    }
}
