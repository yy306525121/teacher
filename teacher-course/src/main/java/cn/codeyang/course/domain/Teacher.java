package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import cn.codeyang.common.jackson.BigDecimalSerializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_teacher")
public class Teacher extends BaseEntity {

    private Long id;

    private String name;

    /**
     * 0-在职
     * 1-离职
     */
    private Integer status;

    /**
     * 基础工资
     */
    @JsonSerialize(using = BigDecimalSerializable.class)
    private BigDecimal basicSalary;

    /**
     * 课时费
     */
    @JsonSerialize(using = BigDecimalSerializable.class)
    private BigDecimal courseSalary;

}
