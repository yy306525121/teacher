package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_time_slot")
public class TimeSlot extends BaseEntity {
    private Long id;

    private Integer dayOfWeek;

    private int sortOfDay;
}
