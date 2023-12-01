package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 科目
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course")
public class Course extends BaseEntity {

    private Long id;

    private String name;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}