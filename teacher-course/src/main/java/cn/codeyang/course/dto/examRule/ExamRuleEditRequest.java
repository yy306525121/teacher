package cn.codeyang.course.dto.examRule;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ExamRuleEditRequest implements Serializable {
    private static final long serialVersionUID = 3571642989719061727L;
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
