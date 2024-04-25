package cn.codeyang.course.calculate;

import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.enums.CourseTypeEnum;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.CourseTypeService;
import cn.codeyang.course.service.TeacherService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yangzy
 */
public interface CourseFeeCalculate {
    List<CourseFee> calculate(LocalDate startDate, LocalDate endDate, List<CourseFee> courseFeeList);


    default List<CourseFee> calculateMorningFee(TeacherService teacherService,
                                                List<CoursePlan> coursePlanList,
                                                BigDecimal price,
                                                Integer week,
                                                LocalDate currentDate) {
        List<CourseFee> courseFeeList = new ArrayList<>();

        Set<String> unionCoursePlanList = coursePlanList.stream().map(coursePlan -> coursePlan.getSubjectId() + StrUtil.SLASH + coursePlan.getTimeSlotId()).collect(Collectors.toSet());
        for (String coursePlanStr : unionCoursePlanList) {
            List<String> coursePlanStrSplit = StrUtil.split(coursePlanStr, StrUtil.SLASH);
            String subjectId = coursePlanStrSplit.get(0);
            String timeSlotId = coursePlanStrSplit.get(1);
            List<Teacher> teacherList = teacherService.getListBySubjectId(subjectId);

            for (Teacher teacher : teacherList) {
                CourseFee courseFee = new CourseFee();
                courseFee.setCount(price);
                courseFee.setTeacherId(teacher.getId());
                courseFee.setSubjectId(Long.valueOf(subjectId));
                courseFee.setWeek(week);
                courseFee.setTimeSlotId(Long.valueOf(timeSlotId));
                courseFee.setDate(currentDate);
                courseFeeList.add(courseFee);
            }

        }
        return courseFeeList;
    }
}
