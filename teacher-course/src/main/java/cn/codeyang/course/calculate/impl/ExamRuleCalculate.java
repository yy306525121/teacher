package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.ExamRule;
import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.dto.coursefee.IgnoreItemDto;
import cn.codeyang.course.service.ClassInfoService;
import cn.codeyang.course.service.ExamRuleService;
import cn.codeyang.course.service.TimeSlotService;
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
@Order(6)
@Component
@RequiredArgsConstructor
public class ExamRuleCalculate implements CourseFeeCalculate {
    private final ExamRuleService examRuleService;
    private final ClassInfoService classInfoService;
    private final TimeSlotService timeSlotService;

    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        List<ExamRule> list = examRuleService.getListByDate(startDate, endDate);

        // 获取放假的日期和节次
        List<IgnoreItemDto> ignoreItemList = getIgnoreItem(startDate, endDate, list);

        for (IgnoreItemDto ignoreItem : ignoreItemList) {
            courseFeeList.removeIf(fee -> fee.getDate().equals(ignoreItem.getDate()) && fee.getClassInfoId().equals(ignoreItem.getClassInfo().getId()) && fee.getTimeSlotId().equals(ignoreItem.getTimeSlot().getId()));
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
