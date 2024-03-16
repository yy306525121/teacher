package cn.codeyang.course.service;

import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.teacher.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TeacherService extends IService<Teacher> {
    List<Teacher> list(TeacherPageRequest request);

    int saveTeacher(TeacherAddRequest request);

    int updateTeacher(TeacherUpdateRequest request);

    TeacherInfoRspDto getInfo(Long id);

    IPage<Teacher> selectPageList(TeacherPageRequest request);

    Teacher getByName(String name);

    List<Teacher> selectListBySubjectName(String subjectName, List<Long> classInfoIdList);

    Teacher getByNameAndCreate(String teacherName);
}
