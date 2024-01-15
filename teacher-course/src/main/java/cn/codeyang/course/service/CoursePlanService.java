package cn.codeyang.course.service;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanListRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanListRspDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CoursePlanService extends IService<CoursePlan> {
    List<CoursePlanListRspDto> selectListByClassInfoId(Long classInfoId);

    List<CoursePlan> selectListByWeekAndTeacherId(int week, Long teacherId);
}
