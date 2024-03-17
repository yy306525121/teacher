package cn.codeyang.course.service.impl;

import cn.codeyang.course.constant.CourseConstant;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanFilterDto;
import cn.codeyang.course.dto.coursefee.CourseFeeDetailRspDto;
import cn.codeyang.course.dto.coursefee.CourseFeePageRspDto;
import cn.codeyang.course.mapper.CourseFeeMapper;
import cn.codeyang.course.service.CourseFeeService;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.SubjectService;
import cn.codeyang.course.service.TeacherService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseFeeServiceImpl extends ServiceImpl<CourseFeeMapper, CourseFee> implements CourseFeeService {
    private final CoursePlanService coursePlanService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;


    @Override
    public IPage<CourseFeePageRspDto> selectPageList(Page<CourseFeePageRspDto> page, LocalDate start, LocalDate end) {
        return baseMapper.selectPageList(page, start, end);
    }

    @Override
    public void calculate(Long teacherId, LocalDate start, LocalDate end) {
        // 1. 清除原有数据
        this.baseMapper.delete(Wrappers.<CourseFee>lambdaQuery().ge(CourseFee::getDate, start).le(CourseFee::getDate, end).eq(teacherId != null, CourseFee::getTeacherId, teacherId));

        // 2.开始计算课时费
        LocalDate current = start;
        List<CourseFee> coursePlanList = new ArrayList<>();
        while (!current.isAfter(end)) {
            coursePlanList.addAll(calculate(teacherId, current));
            // 下一天
            current = current.plus(1, ChronoUnit.DAYS);
        }

        this.saveBatch(coursePlanList);
    }

    @Override
    public List<CourseFeeDetailRspDto> selectListGroupByDate(Long teacherId, LocalDate start, LocalDate end) {
        return baseMapper.selectListGroupByDate(teacherId, start, end);
    }

    /**
     * 计算指定日期的课时
     *
     * @param date
     */
    private List<CourseFee> calculate(Long teacherId, LocalDate date) {
        int week = date.getDayOfWeek().getValue();
        // 1. 查询指定周下的所有课程计划
        List<CoursePlanFilterDto> filter = new ArrayList<>();
        if (date.getDayOfMonth() == 26) {
            // 高三第九节课开始
            filter.add(new CoursePlanFilterDto(1739967916475428866L, 8));
        } else if (date.getDayOfMonth() == 27) {
            // 高三所有课
            filter.add(new CoursePlanFilterDto(1739967916475428866L, null));
            // 高一第11节课开始
            filter.add(new CoursePlanFilterDto(1739967870933676034L, 11));
            // 高二第11节课开始
            filter.add(new CoursePlanFilterDto(1739967895663292418L, 11));
        }
        List<CoursePlanDto> coursePlans = coursePlanService.selectListByWeekAndTeacherId(week, teacherId, filter);

        List<CourseFee> courseFeeList = new ArrayList<>(coursePlans.size());

        long count = coursePlans.stream().filter(coursePlan -> coursePlan.getCourseType().getType() == 3).count();
        if (count > 0) {
            // 计算早自习
            // 查看

            courseFeeList.addAll(calculateMorningEarly(date, week, filter));
        }


        for (CoursePlanDto coursePlan : coursePlans) {
            if (coursePlan.getTeacher() == null) {
                continue;
            }

            CourseFee courseFee = new CourseFee();
            courseFee.setCount(coursePlan.getCourseType().getPrice());
            courseFee.setDate(date);
            courseFee.setTeacherId(coursePlan.getTeacher().getId());
            courseFee.setClassInfoId(coursePlan.getClassInfo().getId());
            if (coursePlan.getSubject() != null) {
                // 白天自习课无课程
                courseFee.setSubjectId(coursePlan.getSubject().getId());
            }
            courseFee.setWeek(coursePlan.getDayOfWeek());
            courseFee.setNumInDay(coursePlan.getTimeSlot().getSortInDay());
            courseFeeList.add(courseFee);
        }

        return courseFeeList;
    }

    // 计算早自习课时
    private List<CourseFee> calculateMorningEarly(LocalDate date, int week, List<CoursePlanFilterDto> coursePlanFilterList) {
        List<CourseFee> courseFeeList = new ArrayList<>();

        List<Long> classInfoIdList = new ArrayList<>();
        if (CollUtil.isNotEmpty(coursePlanFilterList)) {
            // 获取filter中的所有classInfoId
            classInfoIdList = coursePlanFilterList.stream()
                    .filter(coursePlanFilter -> coursePlanFilter.getSortOfDayStart() == null || coursePlanFilter.getSortOfDayStart() == 1)
                    .map(CoursePlanFilterDto::getClassInfoId).toList();
        }

        List<Teacher> teacherList = new ArrayList<>();
        Subject subject = null;
        if (CourseConstant.chineseMorningEarlyWeek.contains(week)) {
            //语文早自习
            subject = subjectService.getByName(CourseConstant.SUBJECT_CHINESE);
            teacherList = teacherService.selectListBySubjectName(CourseConstant.SUBJECT_CHINESE, classInfoIdList);
        } else if (CourseConstant.englishMorningEarlyWeek.contains(week)) {
            // 英语早自习
            subject = subjectService.getByName(CourseConstant.SUBJECT_ENGLISH);
            teacherList = teacherService.selectListBySubjectName(CourseConstant.SUBJECT_ENGLISH, classInfoIdList);
        }

        for (Teacher teacher : teacherList) {
            CourseFee courseFee = new CourseFee();
            courseFee.setCount(CourseConstant.MORNING_EARLY_FEE);
            courseFee.setTeacherId(teacher.getId());
            if (subject != null) {
                courseFee.setSubjectId(subject.getId());
            }
            courseFee.setWeek(week);
            courseFee.setNumInDay(1);
            courseFee.setDate(date);
            courseFeeList.add(courseFee);
        }
        return courseFeeList;
    }
}
