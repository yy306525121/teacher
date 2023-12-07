package cn.codeyang.course.service;

import cn.codeyang.course.domain.TeacherSubject;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeacherSubjectService extends IService<TeacherSubject> {
    int deleteByTeacherId(Long teacherId);
}
