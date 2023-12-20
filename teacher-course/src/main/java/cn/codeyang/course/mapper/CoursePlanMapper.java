package cn.codeyang.course.mapper;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanListRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanListRspDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CoursePlanMapper extends BaseMapper<CoursePlan> {
    List<CoursePlanListRspDto> selectList(CoursePlanListRequest request);
}
