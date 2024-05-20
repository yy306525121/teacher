package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.dto.courseplan.CoursePlanChangeRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanFilterDto;
import cn.codeyang.course.enums.CourseTypeEnum;
import cn.codeyang.course.mapper.CoursePlanMapper;
import cn.codeyang.course.mapper.CourseTypeMapper;
import cn.codeyang.course.opta.domain.CoursePlanOpta;
import cn.codeyang.course.opta.domain.CoursePlanSolution;
import cn.codeyang.course.opta.domain.CoursePlanWeek;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.CourseTypeService;
import cn.codeyang.course.service.TimeSlotService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CoursePlanServiceImpl extends ServiceImpl<CoursePlanMapper, CoursePlan> implements CoursePlanService {
    private final TimeSlotService timeSlotService;
    private final CourseTypeMapper courseTypeMapper;

    @Override
    public List<CoursePlanDto> selectListByClassInfoId(Long classInfoId) {
        return baseMapper.selectListByClassInfoId(classInfoId);
    }

    @Override
    public List<CoursePlanDto> selectListByWeekAndTeacherId(Integer week, Long teacherId, List<CoursePlanFilterDto> filter, LocalDate date) {
        return baseMapper.selectListByWeekAndTeacherId(week, teacherId, filter, LocalDateTimeUtil.format(date, DatePattern.NORM_DATE_PATTERN));
    }

    @Override
    public List<CoursePlan> selectListByClassInfoIdList(List<Long> classInfoIdList, LocalDate date) {
        return this.baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .in(CoursePlan::getClassInfoId, classInfoIdList)
                .ge(date != null, CoursePlan::getStart, date)
                .le(date != null, CoursePlan::getEnd, date));
    }

    @Override
    public void saveSolution(CoursePlanSolution coursePlanSolution) {
        List<CoursePlanOpta> coursePlanList = coursePlanSolution.getCoursePlanList();
        for (CoursePlanOpta coursePlanOpta : coursePlanList) {
            System.out.println(coursePlanOpta);
        }
    }

    @Override
    public CoursePlanSolution selectProbjemById(Long problemId) {
        List<CoursePlanOpta> coursePlanList = new ArrayList<>();
        Long coursePlanId = 1L;

        CoursePlanOpta coursePlan1 = new CoursePlanOpta();
        coursePlan1.setId(++coursePlanId);
        coursePlan1.setTeacherId(1L);
        coursePlan1.setSubjectId(1L);
        coursePlan1.setClassInfoId(3L);
        coursePlan1.setCourseTypeId(1L);
        CoursePlanOpta coursePlan2 = new CoursePlanOpta();
        coursePlan2.setId(++coursePlanId);
        coursePlan2.setTeacherId(1L);
        coursePlan2.setSubjectId(1L);
        coursePlan2.setClassInfoId(3L);
        coursePlan2.setCourseTypeId(1L);
        coursePlanList.add(coursePlan1);
        coursePlanList.add(coursePlan2);

        List<CoursePlanWeek> weekList = new ArrayList<>();
        Long coursePlanWeekId = 1L;
        for (int i = 0; i < 6; i++) {
            CoursePlanWeek coursePlanWeek = new CoursePlanWeek();
            coursePlanWeek.setId(++coursePlanWeekId);
            coursePlanWeek.setWeek(i + 1);
            weekList.add(coursePlanWeek);
        }

        CoursePlanSolution coursePlanSolution = new CoursePlanSolution();
        coursePlanSolution.setTimeSlotList(timeSlotService.list());
        coursePlanSolution.setCoursePlanList(coursePlanList);
        coursePlanSolution.setCoursePlanWeekList(weekList);
        return coursePlanSolution;
    }

    @Override
    public List<CoursePlanDto> selectByClassInfoIdOrTeacherId(Long classInfoId, Long teacherId) {
        return baseMapper.selectByClassInfoIdOrTeacherId(classInfoId, teacherId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(CoursePlanChangeRequest request) {
        // 日期
        LocalDate date = request.getDate();
        Integer week = request.getWeek();
        Long timeSlotId = request.getTimeSlotId();
        // 班级
        Long classInfoId = request.getClassInfoId();
        // 原有教师
        Long fromTeacherId = request.getFromTeacherId();
        Long toTeacherId = request.getToTeacherId();
        // 原有课程
        Long fromSubjectId = request.getFromSubjectId();
        Long toSubjectId = request.getToSubjectId();

        List<CoursePlan> originCoursePlanList = baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .eq(CoursePlan::getClassInfoId, classInfoId)
                .eq(fromTeacherId != null, CoursePlan::getTeacherId, fromTeacherId)
                .eq(fromSubjectId != null, CoursePlan::getSubjectId, fromSubjectId)
                .eq(week != null, CoursePlan::getDayOfWeek, week)
                .eq(timeSlotId != null, CoursePlan::getTimeSlotId, timeSlotId)
        );

        List<CoursePlan> newCoursePlanList = new ArrayList<>(originCoursePlanList.size());
        originCoursePlanList.forEach(coursePlan -> {
            CoursePlan newCoursePlan = new CoursePlan();
            BeanUtil.copyProperties(coursePlan, newCoursePlan);
            newCoursePlan.setId(null);
            newCoursePlan.setStart(date);
            newCoursePlan.setEnd(LocalDate.of(2999, 1, 1));
            newCoursePlan.setTeacherId(request.getToTeacherId());
            newCoursePlan.setSubjectId(toSubjectId);

            coursePlan.setEnd(date.plusDays(-1));

            newCoursePlanList.add(newCoursePlan);
        });
        // 修改原有课程计划
        this.saveOrUpdateBatch(originCoursePlanList);
        this.saveOrUpdateBatch(newCoursePlanList);
    }

    @Override
    public List<CoursePlan> selectListByWeekAndCourseType(LocalDate date, int week, Integer type) {
        return this.baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .eq(CoursePlan::getDayOfWeek, week)
                .eq(CoursePlan::getCourseTypeId, type)
                .le(CoursePlan::getStart, date)
                .ge(CoursePlan::getEnd, date));
    }

    @Override
    public List<CoursePlanDto> selectListByDateAndWeekAndClassInfoId(LocalDate date, Integer week, Long classInfoId) {
        return baseMapper.selectListByDateAndWeekAndClassInfoId(date, week, classInfoId);
    }

    @Override
    public List<CoursePlan> selectListByClassInfoIdsAndDate(List<Long> classInfoIdList, LocalDate date) {
        return baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .le(CoursePlan::getStart, date)
                .ge(CoursePlan::getEnd, date)
                .in(CoursePlan::getClassInfoId, classInfoIdList));
    }

    @Override
    public List<CoursePlan> selectListByOnlyClassInfoIdsAndSubject(List<Long> classInfoIdList, Long subjectId) {
        // 1 查询该课程不再这些班级的所有老师
        List<CoursePlan> coursePlans = baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery().in(CoursePlan::getClassInfoId, classInfoIdList).eq(CoursePlan::getSubjectId, subjectId));
        List<Long> teacherIds = coursePlans.stream().map(CoursePlan::getTeacherId).filter(Objects::nonNull).toList();

        // 排除早自习， 因为早自习课程计划教师id为空
        CourseType courseType = courseTypeMapper.selectOne(Wrappers.<CourseType>lambdaQuery().eq(CourseType::getType, CourseTypeEnum.MORNING.getType()));
        return baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .ne(CoursePlan::getCourseTypeId, courseType.getId())
                .eq(CoursePlan::getSubjectId, subjectId)
                .in(CoursePlan::getClassInfoId, classInfoIdList)
                .in(CoursePlan::getTeacherId, teacherIds));
    }

    @Override
    public List<CoursePlan> selectListByClassInfoIdsAndSubjectId(List<Long> classInfoIdList, Long subjectId) {
        return baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery().in(CoursePlan::getClassInfoId, classInfoIdList).eq(CoursePlan::getSubjectId, subjectId));

    }

    @Override
    public List<CoursePlan> selectListByClassInfoIdsNotInAndSubjectIdAndTeacherIds(List<Long> classInfoIdList, Long subjectId, List<Long> teacherIds) {
        return baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery().notIn(CoursePlan::getClassInfoId, classInfoIdList).eq(CoursePlan::getSubjectId, subjectId).in(CoursePlan::getTeacherId, teacherIds));
    }


}
