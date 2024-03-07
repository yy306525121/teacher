package cn.codeyang.course.opta.constraint;

import cn.codeyang.course.opta.domain.CoursePlanOpta;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

/**
 * @author yangzy
 */
public class CoursePlanConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                teacherConflict(constraintFactory),
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
}
