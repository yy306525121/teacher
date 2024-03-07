package cn.codeyang.course.dto.subject;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SubjectUpdateRequest implements Serializable {
    @NotNull(message = "主键不能为空")
    private Long id;

    @NotEmpty(message = "科目名称不能为空")
    private String name;

    @NotNull(message = "排序不能为空")
    private Integer sort;
}
