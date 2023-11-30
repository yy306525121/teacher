package cn.codeyang.course.dto.grade;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class GradeAddRequest implements Serializable {
    @NotEmpty(message = "年级不能为空")
    private String gradeName;

    private Long parentId = 0L;
}
