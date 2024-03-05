package cn.codeyang.course.service.impl;

import cn.codeyang.course.optaplanner.domain.OptaCoursePlan;
import cn.codeyang.course.optaplanner.domain.OptaSolution;
import cn.codeyang.course.service.OptaSolutionService;
import cn.codeyang.course.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzy
 */
@Service
@RequiredArgsConstructor
public class OptaSolutionServiceImpl implements OptaSolutionService {
    private final TimeSlotService timeSlotService;

    @Override
    public OptaSolution findById(Long id) {
        OptaSolution lessonPlanSolution = new OptaSolution();
        lessonPlanSolution.setTimeSlotList(timeSlotService.list());


        List<OptaCoursePlan> coursePlanList = new ArrayList<>();

        Long lessonPlanId = 1L;

        OptaCoursePlan coursePlan1 = new OptaCoursePlan();
        coursePlan1.setId(++lessonPlanId);
        coursePlan1.setTeacherId(1L);
        coursePlan1.setSubjectId(1L);
        coursePlan1.setClassInfoId(1L);
        coursePlan1.setLessonType(1);

        OptaCoursePlan coursePlan2 = new OptaCoursePlan();
        coursePlan2.setId(++lessonPlanId);
        coursePlan2.setTeacherId(1L);
        coursePlan2.setSubjectId(1L);
        coursePlan2.setClassInfoId(1L);
        coursePlan2.setLessonType(1);

        coursePlanList.add(coursePlan1);
        coursePlanList.add(coursePlan2);

        lessonPlanSolution.setCoursePlanList(coursePlanList);

        return lessonPlanSolution;
    }

    @Override
    public void save(OptaSolution optaSolution) {
        List<OptaCoursePlan> coursePlanList = optaSolution.getCoursePlanList();
        for (OptaCoursePlan optaCoursePlan : coursePlanList) {
            System.out.println(optaCoursePlan);
        }
    }
}
