package cn.codeyang.course.dto.courseplan;

import cn.codeyang.course.domain.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangzy
 */
@Data
public class CoursePlanDto implements Serializable {
    private static final long serialVersionUID = -9007903338014645932L;
    private Long id;

    private ClassInfo classInfo;

    private Teacher teacher;

    private Subject subject;

    private TimeSlot timeSlot;

    private CourseType courseType;
}
