package cn.codeyang.course.domain;

import cn.codeyang.common.annotation.Excel;
import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_fill_rule")
public class FillRule extends BaseEntity {
    private Long id;

    private String classInfoId;

    /**
     * 补课日期
     */
    private LocalDate date;

    /**
     * 补周几的课
     */
    private Integer week;

    /**
     * 开始补课节次
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long startTimeSlotId;

    /**
     * 结束补课课时
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long endTimeSlotId;
}
