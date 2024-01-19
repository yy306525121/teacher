package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_plan")
public class CoursePlan extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long classInfoId;

    private Long teacherId;

    private Long subjectId;

    /**
     * 课程时间外键
     */
    private Long timeSlotId;

    /**
     * 课程类型
     * 1-值班课时
     * 2-正常课时
     */
    private Integer courseTypeId;
}
