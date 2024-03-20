package cn.codeyang.course.dto.feeRule;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CourseFeeRulePageResponse implements Serializable {
    private static final long serialVersionUID = 2138433515233315377L;

    private Long id;

    private Integer type;

    private String classInfoId;

    private LocalDate startDate;

    private Long startTimeSlotId;
    private Integer startSortInDay;

    private LocalDate endDate;
    private Long endTimeSlotId;
    private Integer endSortInDay;
}
