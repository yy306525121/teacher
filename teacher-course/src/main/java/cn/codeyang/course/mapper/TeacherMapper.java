package cn.codeyang.course.mapper;

import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.teacher.TeacherInfoRspDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper extends BaseMapper<Teacher> {
    // IPage<TeacherPageResponse> selectPage(IPage<?> page, @Param("name") String name, @Param("status") String status);

    TeacherInfoRspDto getInfo(Long id);

    List<Teacher> selectListBySubjectName(@Param("subjectName") String subjectName, @Param("teacherIdList") List<Long> teacherIdList);

    List<Teacher> selectListBySubjectId(@Param("subjectId") String subjectId);
}
