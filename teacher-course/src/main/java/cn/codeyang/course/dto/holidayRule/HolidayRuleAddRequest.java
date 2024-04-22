package cn.codeyang.course.dto.holidayRule;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class HolidayRuleAddRequest implements Serializable {
    private static final long serialVersionUID = -8130691764569870442L;

    private String classInfoId;

    @NotNull(message = "放假开始日期不能为空")
    private LocalDate startDate;
    @NotNull(message = "放假开始节次不能为空")
    private Long startTimeSlotId;
    @NotNull(message = "放假结束日期不能为空")
    private LocalDate endDate;
    @NotNull(message = "放假结束节次不能为空")
    private Long endTimeSlotId;
}
