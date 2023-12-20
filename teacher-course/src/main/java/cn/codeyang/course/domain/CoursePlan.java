package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_plan")
public class CoursePlan extends BaseEntity {
    private Long id;

    private Long classInfoId;

    private Long teacherId;

    private Long subjectId;

    private Integer week;

    private Integer numInDay;
}
