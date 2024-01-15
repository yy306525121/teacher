package cn.codeyang.course.service;

import cn.codeyang.course.dto.lessonfee.CourseFeePageReqDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LessonFeeServiceTest {
    @Autowired
    private CourseFeeService lessonFeeService;

    @Test
    public void selectPageList() {
        CourseFeePageReqDto request = new CourseFeePageReqDto();
        // request.setStart(LocalDate.of(2023, 12, 12));
        // request.setEnd(LocalDate.of(2023, 12, 30));
        // request.setPage(new Page<>(1, 10));
        // IPage<LessonFeePageRspDto> result = lessonFeeService.selectPageList(request);
        // System.out.println(result);
    }
}
