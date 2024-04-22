package cn.codeyang.course.service;

import cn.codeyang.course.calculate.CourseFeeCalculateHandleChainService;
import cn.codeyang.course.domain.CourseFee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

/**
 * @author yangzy
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class CalculateTest {

    @Autowired
    private CourseFeeCalculateHandleChainService courseFeeCalculateHandleChainService;

    @Test
    public void test1() {
        List<CourseFee> courseFeeList = courseFeeCalculateHandleChainService.execute(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 29));
        System.out.println(courseFeeList);
    }
}
