package cn.codeyang.course.opta.domain;

import cn.codeyang.course.domain.TimeSlot;
import lombok.Data;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * @author yangzy
 */
@Data
@PlanningEntity
public class CoursePlanOpta {
    @PlanningId
    private Long id;

    private Long teacherId;
    private Long subjectId;
    private Long classInfoId;
    private Long courseTypeId;

    @PlanningVariable
    private TimeSlot timeSlot;

    @PlanningVariable
    private CoursePlanWeek week;
}
