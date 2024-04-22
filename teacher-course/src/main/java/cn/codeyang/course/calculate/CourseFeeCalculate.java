package cn.codeyang.course.calculate;

import cn.codeyang.course.domain.CourseFee;

import java.time.LocalDate;
import java.util.List;

/**
 * @author yangzy
 */
public interface CourseFeeCalculate {
    List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList);
}
