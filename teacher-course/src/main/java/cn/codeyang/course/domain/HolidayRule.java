package cn.codeyang.course.domain;

import cn.codeyang.common.annotation.Excel;
import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_holiday_rule")
public class HolidayRule extends BaseEntity {
    private Long id;

    @Excel(name = "班级ID")
    private String classInfoId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDate startDate;

    /** 开始课时 */
    @Excel(name = "开始课时")
    private Long startTimeSlotId;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDate endDate;

    /** 结束课时 */
    @Excel(name = "结束课时")
    private Long endTimeSlotId;
}
