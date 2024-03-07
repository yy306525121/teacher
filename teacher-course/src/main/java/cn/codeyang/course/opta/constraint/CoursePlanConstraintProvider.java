package cn.codeyang.course.opta.constraint;

import cn.codeyang.course.opta.domain.CoursePlanOpta;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.Duration;

/**
 * @author yangzy
 */
public class CoursePlanConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                teacherConflict(constraintFactory),
                classInfoConflict(constraintFactory),
                courseTypeConflict(constraintFactory),
                teacherTimeEfficiency(constraintFactory)
        };
    }

    /**
     * 一个老师同一时间只能上一节课
     * @param constraintFactory
     * @return
     */
    Constraint teacherConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                // Select each pair of 2 different lessons ...
                .forEachUniquePair(CoursePlanOpta.class,
                        // ... in the same timeslot ...
                        Joiners.equal(CoursePlanOpta::getTimeSlot),
                        Joiners.equal(CoursePlanOpta::getWeek),
                        Joiners.equal(CoursePlanOpta::getTeacherId))
                // ... and penalize each pair with a hard weight.
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("teacher conflict");
    }

    /**
     * 一个班级同一时间只能上一节课
     * @param constraintFactory
     * @return
     */
    Constraint classInfoConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                // Select each pair of 2 different lessons ...
                .forEachUniquePair(CoursePlanOpta.class,
                        // ... in the same timeslot ...
                        Joiners.equal(CoursePlanOpta::getTimeSlot),
                        Joiners.equal(CoursePlanOpta::getWeek),
                        Joiners.equal(CoursePlanOpta::getClassInfoId))
                // ... and penalize each pair with a hard weight.
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("classInfo conflict");
    }

    /**
     * 课程类型约束，正常课时和白天自习课不能安排到早晚自习
     * @param constraintFactory
     * @return
     */
    Constraint courseTypeConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                // Select each pair of 2 different lessons ...
                .forEach(CoursePlanOpta.class)
                .filter((coursePlan) -> coursePlan.getCourseTypeId() == 1 && (coursePlan.getTimeSlot().getType()==1 || coursePlan.getTimeSlot().getType()==2))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("courseType conflict");
    }


    /**
     * 教师喜欢按顺序授课，不喜欢课程之间有空隙
     * @param constraintFactory
     * @return
     */
    Constraint teacherTimeEfficiency(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(CoursePlanOpta.class)
                .join(CoursePlanOpta.class, Joiners.equal(CoursePlanOpta::getTeacherId),
                        Joiners.equal((coursePlan) -> coursePlan.getWeek().getWeek()))
                .filter((coursePlan1, coursePlan2) -> {
                    Duration between = Duration.between(coursePlan1.getTimeSlot().getEndTime(), coursePlan2.getTimeSlot().getStartTime());
                    return !between.isNegative() && between.compareTo(Duration.ofMinutes(30)) <= 0;
                })
                .reward(HardSoftScore.ONE_SOFT)
                .asConstraint("Teacher time efficiency");
    }


}
