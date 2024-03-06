package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 排课任务
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_schedule")
public class CourseSchedule extends BaseEntity {

    /**
     * 排课任务名
     */
    private String name;

    /**
     * 每周上课天数
     */
    private Integer courseDayNum;
}
