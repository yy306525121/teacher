package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_time_slot")
public class TimeSlot extends BaseEntity {
    @PlanningId
    private Long id;

    /**
     * 周几
     */
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * 早自习、晚自习、课时
     */
    private Integer type;

}
