package cn.codeyang.course.optaplanner.domain;

import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.TimeSlot;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;

import java.util.List;

/**
 * 排课解决方案
 * @author yangzy
 */
@PlanningSolution
public class CoursePlanSolution {
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeSlotList;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<ClassInfo> classInfoList;

}
