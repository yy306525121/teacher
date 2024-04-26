package cn.codeyang.course.service;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanChangeRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanFilterDto;
import cn.codeyang.course.opta.domain.CoursePlanSolution;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface CoursePlanService extends IService<CoursePlan> {
    List<CoursePlanDto> selectListByClassInfoId(Long classInfoId);

    List<CoursePlanDto> selectListByWeekAndTeacherId(Integer week, @Nullable Long teacherId, @Nullable List<CoursePlanFilterDto> filter, LocalDate date);

    /**
     * 查询指定日期，指定班级下的有效课程计划
     * @param classInfoIdList
     * @param date
     * @return
     */
    List<CoursePlan> selectListByClassInfoIdList(List<Long> classInfoIdList, LocalDate date);

    void saveSolution(CoursePlanSolution coursePlanSolution);

    CoursePlanSolution selectProbjemById(Long problemId);

    /**
     * 通过班级或教师查询课程计划
     * @param classInfoId
     * @param teacherId
     * @return
     */
    List<CoursePlanDto> selectByClassInfoIdOrTeacherId(Long classInfoId, Long teacherId);

    /**
     * 替换课程
     * @param request
     */
    void change(CoursePlanChangeRequest request);

    /**
     * 根据周数和课程类型查询当前日期有效的课程计划
     * @param week 周
     * @param type 课程类型
     * @return
     */
    List<CoursePlan> selectListByWeekAndCourseType(LocalDate date, int week, Integer type);

    /**
     * 根据条件查询课程计划
     * @param date 日期
     * @param week 星期
     * @param classInfoId 班级
     * @return
     */
    List<CoursePlanDto> selectListByDateAndWeekAndClassInfoId(LocalDate date, Integer week, Long classInfoId);

    List<CoursePlan> selectListByClassInfoIdsAndDate(List<Long> classInfoIdList, LocalDate date);

    List<CoursePlan> selectListByClassInfoIdsAndSubject(List<Long> classInfoIdList, Long subjectId);
}
