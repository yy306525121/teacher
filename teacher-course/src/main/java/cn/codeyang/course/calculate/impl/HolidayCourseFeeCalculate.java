package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.HolidayRule;
import cn.codeyang.course.service.HolidayRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Order(3)
@Component
@RequiredArgsConstructor
public class HolidayCourseFeeCalculate implements CourseFeeCalculate {
    private final HolidayRuleService holidayRuleService;
    
    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        List<HolidayRule> list = holidayRuleService.list();
        return null;
    }
}
