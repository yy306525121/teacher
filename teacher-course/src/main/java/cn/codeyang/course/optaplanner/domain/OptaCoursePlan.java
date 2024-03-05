package cn.codeyang.course.optaplanner.domain;

import cn.codeyang.course.domain.TimeSlot;
import lombok.Data;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * @author yangzy
 */
@PlanningEntity
@Data
public class OptaCoursePlan {

    @PlanningId
    private Long id;

    private Long teacherId;
    private Long subjectId;
    private Long classInfoId;

    /**
     *
     */
    private Integer lessonType;

    @PlanningVariable
    private TimeSlot timeSlot;
}
