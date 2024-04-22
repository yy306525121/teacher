package cn.codeyang.course.calculate;

import cn.codeyang.course.domain.CourseFee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzy
 */
@Component
@RequiredArgsConstructor
public class CourseFeeCalculateHandleChainService {
    private final List<CourseFeeCalculate> calculateList;

    public List<CourseFee> execute(LocalDate startDate, LocalDate endDate) {
        List<CourseFee> courseFeeList = new ArrayList<>();
        for (CourseFeeCalculate calculate : calculateList) {
            courseFeeList = calculate.calculate(startDate, endDate, courseFeeList);
        }
        return courseFeeList;
    }
}
