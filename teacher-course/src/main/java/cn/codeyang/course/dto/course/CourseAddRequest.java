package cn.codeyang.course.dto.course;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class CourseAddRequest implements Serializable {
    @NotEmpty(message = "科目名不能为空")
    private String name;
}
