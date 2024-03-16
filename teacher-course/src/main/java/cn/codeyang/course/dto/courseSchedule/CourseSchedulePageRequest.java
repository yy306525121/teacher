package cn.codeyang.course.dto.courseSchedule;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseSchedulePageRequest implements Serializable {
    private String name;
}
