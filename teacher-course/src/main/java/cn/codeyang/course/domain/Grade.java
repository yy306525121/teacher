package cn.codeyang.course.domain;

import cn.codeyang.common.annotation.Excel;
import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_grade")
public class Grade extends BaseEntity {
    /** ID */
    private Long id;

    /** 年级外键 */
    @Excel(name = "年级外键")
    private String gradeName;

    private Long parentId;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
