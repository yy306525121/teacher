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
@Order(7)
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

        for (CourseFee courseFee : courseFeeList) {
            TransferRule transferRule = ruleList.stream().filter(rule -> rule.getOverrideDate().equals(courseFee.getDate()) && rule.getOverrideTimeSlotId().equals(courseFee.getTimeSlotId()) && rule.getOverrideFromTeacherId().equals(courseFee.getTeacherId())).findFirst().orElse(null);
            if (transferRule != null) {
                courseFee.setTeacherId(transferRule.getOverrideToTeacherId());
                if (transferRule.getOverrideToSubjectId() != null) {
                    courseFee.setSubjectId(transferRule.getOverrideToSubjectId());
                }
                if (transferRule.getOverrideToCourseTypeId() != null) {
                    CourseType courseType = courseTypeList.stream().filter(item -> item.getId().equals(transferRule.getOverrideToCourseTypeId())).findFirst().orElse(null);
                    if (courseType != null) {
                        courseFee.setCount(courseType.getPrice());
                    }
                }
            }

        }

        return courseFeeList;
    }
}
