package cn.codeyang.course.domain;

import cn.codeyang.common.annotation.Excel;
import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班级信息
 * 高一
 *   高一(1)班
 *   高一(2)班
 * 高二
 *   高二(1)班
 *   高二(2)班
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_class_info")
public class ClassInfo extends BaseEntity {
    /** ID */

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 年级id
     */
    private Long parentId;


    private Integer sort;
}
