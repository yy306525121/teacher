package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_fee")
public class CourseFee extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private BigDecimal count;

    private Long teacherId;

    private Long classInfoId;

    private Long subjectId;

    private Integer week;

    private Integer numInDay;

    private LocalDate date;
}
