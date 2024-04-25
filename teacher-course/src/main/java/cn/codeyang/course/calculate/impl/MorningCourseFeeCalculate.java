package cn.codeyang.course.calculate.impl;

import cn.codeyang.course.calculate.CourseFeeCalculate;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.enums.CourseTypeEnum;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.CourseTypeService;
import cn.codeyang.course.service.TeacherService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 计算早自习课时
 * @author yangzy
 */
@Order(10)
@Component
@RequiredArgsConstructor
public class MorningCourseFeeCalculate implements CourseFeeCalculate {

    /**
     * 早自习的type值
     */
    private static final int TYPE = CourseTypeEnum.MORNING.getType();

    private final CoursePlanService coursePlanService;
    private final CourseTypeService courseTypeService;
    private final TeacherService teacherService;

    /**
     * 早自习课时费
     */
    private BigDecimal price;

    @SneakyThrows
    @PostConstruct
    public void init(){
        CourseType courseType = courseTypeService.selectByType(TYPE);
        if (courseType == null) {
            throw new Exception("请检查t_course_type表中是否存在早自习课程类型");
        }
        price = courseType.getPrice();
    }


    @Override
    public List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList) {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            courseFeeList.addAll(calculateMorning(currentDate));
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }
        return courseFeeList;
    }

    private List<CourseFee> calculateMorning(LocalDate currentDate) {
        List<CourseFee> courseFeeList = new ArrayList<>();

        // 1. 获取周的value值
        int week = currentDate.getDayOfWeek().getValue();
        List<CoursePlan> coursePlanList = coursePlanService.selectListByWeekAndCourseType(currentDate, week, TYPE);
        if (CollUtil.isNotEmpty(coursePlanList)) {
            courseFeeList = calculateMorningFee(teacherService, coursePlanList, price, week, currentDate);
        }

        return courseFeeList;
    }
}
