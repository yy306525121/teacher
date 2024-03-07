package cn.codeyang.course.opta.domain;

import cn.codeyang.course.domain.TimeSlot;
import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

/**
 * @author yangzy
 */
@Data
@PlanningSolution
public class CoursePlanSolution {
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeSlotList;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<CoursePlanWeek> coursePlanWeekList;

    @PlanningEntityCollectionProperty
    private List<CoursePlanOpta> coursePlanList;

    @PlanningScore
    private HardSoftScore score;

    private SolverStatus solverStatus;
}
