package cn.codeyang.course.service;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanFilterDto;
import cn.codeyang.course.opta.domain.CoursePlanSolution;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import java.time.DayOfWeek;
import java.util.List;

public interface CoursePlanService extends IService<CoursePlan> {
    List<CoursePlanDto> selectListByClassInfoId(Long classInfoId);

    List<CoursePlanDto> selectListByWeekAndTeacherId(Integer week, @Nullable Long teacherId, @Nullable List<CoursePlanFilterDto> filter);

    List<CoursePlan> selectListByClassInfoIdList(List<Long> classInfoIdList);

    void saveSolution(CoursePlanSolution coursePlanSolution);

    CoursePlanSolution selectProbjemById(Long problemId);

    /**
     * 通过班级或教师查询课程计划
     * @param classInfoId
     * @param teacherId
     * @return
     */
    List<CoursePlanDto> selectByClassInfoIdOrTeacherId(Long classInfoId, Long teacherId);
}
