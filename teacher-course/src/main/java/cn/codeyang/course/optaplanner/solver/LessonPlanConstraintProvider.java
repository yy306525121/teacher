package cn.codeyang.course.optaplanner.solver;

import cn.codeyang.course.optaplanner.domain.OptaCoursePlan;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

/**
 * @author yangzy
 */
public class LessonPlanConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                teacherConflict(constraintFactory),
                lessonTypeConflict(constraintFactory),
                classInfoConflict(constraintFactory)
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
                .forEachUniquePair(OptaCoursePlan.class,
                        // ... in the same timeslot ...
                        Joiners.equal(OptaCoursePlan::getTimeSlot),
                        Joiners.equal(OptaCoursePlan::getTeacherId))
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
                .forEachUniquePair(OptaCoursePlan.class,
                        // ... in the same timeslot ...
                        Joiners.equal(OptaCoursePlan::getTimeSlot),
                        Joiners.equal(OptaCoursePlan::getClassInfoId))
                // ... and penalize each pair with a hard weight.
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("classInfo conflict");
    }

    /**
     * 课程类型约束，正常课时和白天自习课不能安排到早晚自习
     * @param constraintFactory
     * @return
     */
    Constraint lessonTypeConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                // Select each pair of 2 different lessons ...
                .forEach(OptaCoursePlan.class)
                .filter((lesson) -> lesson.getLessonType() == 1 && (lesson.getTimeSlot().getType()==1 || lesson.getTimeSlot().getType()==3))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("lessType conflict");
    }
}
