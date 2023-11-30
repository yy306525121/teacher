package cn.codeyang.course.dto.course;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CourseUpdateRequest implements Serializable {
    @NotNull(message = "主键不能为空")
    private Long id;

    @NotEmpty(message = "科目名称不能为空")
    private String name;
}
