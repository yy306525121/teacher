package cn.codeyang.course.dto.fillRule;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class FillRuleEditRequest implements Serializable {

    private static final long serialVersionUID = -3310815497219606610L;

    private String classInfoId;

    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 补课日期
     */
    @NotNull(message = "日期不能为空")
    private LocalDate date;

    /**
     * 补周几的课
     */
    @NotNull(message = "周不能为空")
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
