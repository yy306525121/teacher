package cn.codeyang.course.dto.examRule;

import cn.codeyang.common.annotation.Excel;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.TimeSlot;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamRulePageRsp implements Serializable {
    private Long id;

    private String classInfoId;

    private List<ClassInfo> classInfoList;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDate startDate;

    /** 开始课时 */
    @Excel(name = "开始课时")
    private Long startTimeSlotId;

    private TimeSlot startTimeSlot;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDate endDate;

    /** 结束课时 */
    @Excel(name = "结束课时")
    private Long endTimeSlotId;

    private TimeSlot endTimeSlot;

    private LocalDateTime createTime;
}
