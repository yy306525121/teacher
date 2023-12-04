package cn.codeyang.course.dto.subject;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SubjectAddRequest implements Serializable {
    @NotEmpty(message = "科目名不能为空")
    private String name;

    @NotNull(message = "排序不能为空")
    private Integer sort;
}
