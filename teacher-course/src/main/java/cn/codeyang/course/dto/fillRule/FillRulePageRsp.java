package cn.codeyang.course.dto.fillRule;

import cn.codeyang.course.domain.TimeSlot;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class FillRulePageRsp implements Serializable {
    private Long id;

    /**
     * 补课日期
     */
    private LocalDate date;

    /**
     * 补周几的课
     */
    private Integer week;

    /**
     * 开始补课节次
     */
    private Long startTimeSlotId;

    private TimeSlot startTimeSlot;

    /**
     * 结束补课课时
     */
    private Long endTimeSlotId;

    private TimeSlot endTimeSlot;
}
