package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanFilterDto;
import cn.codeyang.course.mapper.CoursePlanMapper;
import cn.codeyang.course.opta.domain.CoursePlanOpta;
import cn.codeyang.course.opta.domain.CoursePlanSolution;
import cn.codeyang.course.opta.domain.CoursePlanWeek;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.TimeSlotService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursePlanServiceImpl extends ServiceImpl<CoursePlanMapper, CoursePlan> implements CoursePlanService {
    private final TimeSlotService timeSlotService;

    @Override
    public List<CoursePlanDto> selectListByClassInfoId(Long classInfoId) {
        return baseMapper.selectListByClassInfoId(classInfoId);
    }

    @Override
    public List<CoursePlanDto> selectListByWeekAndTeacherId(Integer week, Long teacherId, List<CoursePlanFilterDto> filter) {
        return baseMapper.selectListByWeekAndTeacherId(week, teacherId, filter);
    }

    @Override
    public List<CoursePlan> selectListByClassInfoIdList(List<Long> classInfoIdList) {
        return this.baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .in(CoursePlan::getClassInfoId, classInfoIdList));
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
}
