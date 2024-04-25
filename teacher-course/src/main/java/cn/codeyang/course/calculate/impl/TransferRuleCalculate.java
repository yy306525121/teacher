package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.domain.TransferRule;
import cn.codeyang.course.service.CourseTypeService;
import cn.codeyang.course.service.TransferRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * 调课课时规则
 */
@Order(70)
@Component
@RequiredArgsConstructor
public class TransferRuleCalculate implements CourseFeeCalculate {
    private final TransferRuleService transferRuleService;
    private final CourseTypeService courseTypeService;


    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        // 1. 查询所有适用规则
        List<TransferRule> ruleList = transferRuleService.getListByDate(startDate, endDate);

        List<CourseType> courseTypeList = courseTypeService.list();

        for (TransferRule rule : ruleList) {
            CourseFee courseFee = courseFeeList.stream().filter(item -> item.getDate().equals(rule.getOverrideDate()) && item.getTimeSlotId().equals(rule.getOverrideTimeSlotId()) && item.getTeacherId().equals(rule.getOverrideFromTeacherId())).findFirst().orElse(null);
            if (courseFee != null) {
                courseFee.setTeacherId(rule.getOverrideToTeacherId());

                if (rule.getOverrideToSubjectId() != null) {
                    courseFee.setSubjectId(rule.getOverrideToSubjectId());
                }
                if (rule.getOverrideToCourseTypeId() != null) {
                    CourseType courseType = courseTypeList.stream().filter(item -> item.getId().equals(rule.getOverrideToCourseTypeId())).findFirst().orElse(null);
                    if (courseType != null) {
                        courseFee.setCount(courseType.getPrice());
                    }
                }
            }
        }
        return courseFeeList;
    }
}
