package cn.codeyang.course.mapper;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanFilterDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoursePlanMapper extends BaseMapper<CoursePlan> {
    List<CoursePlanDto> selectListByClassInfoId(Long classInfoId);

    List<CoursePlanDto> selectListByWeekAndTeacherId(@Param("week") Integer week,
                                                     @Param("teacherId") Long teacherId,
                                                     @Param("coursePlanFilterList")List<CoursePlanFilterDto> coursePlanFilterList);

    List<CoursePlanDto> selectByClassInfoIdOrTeacherId(@Param("classInfoId") Long classInfoId, @Param("teacherId") Long teacherId);
}
