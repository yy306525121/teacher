package cn.codeyang.course.service;

import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.teacher.TeacherInfoRspDto;
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
        IPage<Teacher> result = teacherService.selectPageList(request);
        System.out.println(result);
    }

    @Test
    public void testGetInfo() {
        TeacherInfoRspDto info = teacherService.getInfo(1732389869996036097L);
        System.out.println(info);
    }
}
