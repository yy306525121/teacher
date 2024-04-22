package cn.codeyang.course.dto.transferRule;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TransferRuleAddRequest implements Serializable {

    private static final long serialVersionUID = 685174340650103049L;
    /**
     * 调课日期
     */
    @NotNull(message = "调课日期不能为空")
    private LocalDate overrideDate;

    /**
     * 调课节次
     */
    @NotNull(message = "调课节次不能为空")
    private Long overrideTimeSlotId;

    /**
     * 调课前教师
     */
    @NotNull(message = "调课前教师不能为空")
    private Long overrideFromTeacherId;

    /**
     * 调课后教师
     */
    @NotNull(message = "调课后教师不能为空")
    private Long overrideToTeacherId;

    /**
     * 调课前课程
     */
    private Long overrideFromSubjectId;

    /**
     * 调课后课程
     */
    private Long overrideToSubjectId;

    /**
     * 调整前的课程类型
     */
    private Long overrideFromCourseTypeId;

    /**
     * 调整后的课程类型
     */
    private Long overrideToCourseTypeId;
}
