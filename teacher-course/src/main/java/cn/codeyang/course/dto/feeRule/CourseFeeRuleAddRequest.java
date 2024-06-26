package cn.codeyang.course.dto.feeRule;

import cn.codeyang.common.annotation.Excel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CourseFeeRuleAddRequest implements Serializable {

    private static final long serialVersionUID = -794542046482006805L;

    @NotNull(message = "规则类型不能为空")
    private Integer type;

    private String classInfoId;

    private LocalDate startDate;

    private Long startTimeSlotId;
    private LocalDate endDate;
    private Long endTimeSlotId;


    private LocalDate overrideDate;

    private Long overrideTimeSlotId;

    private Long overrideFromTeacherId;
    private Long overrideToTeacherId;

    private Long overrideFromSubjectId;
    private Long overrideToSubjectId;

    private Long overrideFromCourseTypeId;
    private Long overrideToCourseTypeId;


}
