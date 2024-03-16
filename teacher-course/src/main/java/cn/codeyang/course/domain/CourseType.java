package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author yangzy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_type")
public class CourseType extends BaseEntity {
    private Long id;

    private String name;

    private Integer type;

    private BigDecimal price;
}
