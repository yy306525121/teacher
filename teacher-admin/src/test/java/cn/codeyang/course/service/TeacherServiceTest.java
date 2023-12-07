package cn.codeyang.course.service;

import cn.codeyang.course.dto.teacher.TeacherPageRequest;
import cn.codeyang.course.dto.teacher.TeacherPageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeacherServiceTest {
    @Autowired
    private TeacherService teacherService;

    @Test
    public void testSelectPage() {
        TeacherPageRequest request = new TeacherPageRequest();
        Page<TeacherPageResponse> page = new Page<>();
        page.setSize(10);
        page.setCurrent(1);
        request.setPage(page);
        IPage<TeacherPageResponse> result = teacherService.selectPage(request);
        System.out.println(result);
    }
}
