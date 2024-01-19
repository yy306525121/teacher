package cn.codeyang.course.mapper;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.DayOfWeek;
import java.util.List;

public interface CoursePlanMapper extends BaseMapper<CoursePlan> {
    List<CoursePlanDto> selectListByClassInfoId(Long classInfoId);

    List<CoursePlanDto> selectListByWeekAndTeacherId(@Param("week") DayOfWeek week, @Param("teacherId") Long teacherId);
}
