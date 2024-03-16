package cn.codeyang.course.dto.courseSchedule;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseScheduleAddRequest implements Serializable {
    @NotEmpty(message = "任务名称不能为空")
    private String name;

    @NotNull(message = "每周天数不能为空")
    private Integer courseDayNum;
}
