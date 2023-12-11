package cn.codeyang.course.mapper;

import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.teacher.TeacherInfoRspDto;
import cn.codeyang.course.dto.teacher.TeacherPageResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper extends BaseMapper<Teacher> {
    // IPage<TeacherPageResponse> selectPage(IPage<?> page, @Param("name") String name, @Param("status") String status);

    TeacherInfoRspDto getInfo(Long id);
}
