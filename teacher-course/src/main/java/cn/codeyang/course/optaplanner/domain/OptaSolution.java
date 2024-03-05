package cn.codeyang.course.optaplanner.domain;

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
public class OptaSolution {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeSlotList;

    @PlanningEntityCollectionProperty
    private List<OptaCoursePlan> coursePlanList;

    @PlanningScore
    private HardSoftScore hardSoftScore;

    private SolverStatus solverStatus;
}
