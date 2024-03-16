package cn.codeyang.course.service;

import cn.codeyang.course.domain.CourseSchedule;
import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.dto.courseSchedule.CourseSchedulePageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CourseScheduleService extends IService<CourseSchedule> {
    List<CourseSchedule> listByName(String name);

    int saveCourseSchedule(CourseSchedulePageRequest request);
}
