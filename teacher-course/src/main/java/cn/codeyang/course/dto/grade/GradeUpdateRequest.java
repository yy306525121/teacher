package cn.codeyang.course.dto.grade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GradeUpdateRequest implements Serializable {
    @NotNull(message = "主键不能为空")
    private Long id;

    @NotEmpty(message = "班级信息不能为空")
    private String gradeName;
}
