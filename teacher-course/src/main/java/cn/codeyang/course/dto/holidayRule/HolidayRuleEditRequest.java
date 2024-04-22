package cn.codeyang.course.dto.holidayRule;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class HolidayRuleEditRequest implements Serializable {

    private static final long serialVersionUID = -3310815497219606610L;
    @NotNull(message = "主键不能为空")
    private Long id;
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
