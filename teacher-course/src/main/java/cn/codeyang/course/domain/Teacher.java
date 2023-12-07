package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String status;

    /**
     * 基础工资
     */
    private BigDecimal basicSalary;


}
