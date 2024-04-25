package cn.codeyang.course.dto.fillRule;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class FillRuleAddRequest implements Serializable {
    private static final long serialVersionUID = -8130691764569870442L;

    private String classInfoId;

    /**
     * 补课日期
     */
    @NotNull(message = "补课日期不能为空")
    private LocalDate date;

    /**
     * 补周几的课
     */
    @NotNull(message = "补课周不能为空")
    private Integer week;

    /**
     * 开始补课节次
     */
    private Long startTimeSlotId;

    /**
     * 结束补课课时
     */
    private Long endTimeSlotId;
}
