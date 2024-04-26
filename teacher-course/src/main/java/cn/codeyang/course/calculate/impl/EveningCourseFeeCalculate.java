package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.enums.CourseTypeEnum;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.CourseTypeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 自习课课时计算
 * @author yangzy
 */
@Order(40)
@Component
@RequiredArgsConstructor
public class EveningCourseFeeCalculate implements CourseFeeCalculate {
    // 正常课时的type值
    private static final int TYPE = CourseTypeEnum.EVENING.getType();

    private final CourseTypeService courseTypeService;
    private final CoursePlanService coursePlanService;

    /**
     * 自习课时课时费
     */
    private BigDecimal price;

    @SneakyThrows
    @PostConstruct
    public void init(){
        CourseType courseType = courseTypeService.selectByType(TYPE);
        if (courseType == null) {
            throw new Exception("请检查t_course_type表中是否存在正常课时课程类型");
        }
        price = courseType.getPrice();
    }

    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            int week = currentDate.getDayOfWeek().getValue();
            courseFeeList.addAll(calculateEvening(coursePlanService, currentDate, week, price));
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }
        return courseFeeList;
    }
}
