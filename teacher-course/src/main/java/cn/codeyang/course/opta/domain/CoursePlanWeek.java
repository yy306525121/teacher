package cn.codeyang.course.opta.domain;

import lombok.Data;
import org.optaplanner.core.api.domain.lookup.PlanningId;

/**
 * @author yangzy
 */
@Data
public class CoursePlanWeek {
    @PlanningId
    private Long id;

    private Integer week;
}
