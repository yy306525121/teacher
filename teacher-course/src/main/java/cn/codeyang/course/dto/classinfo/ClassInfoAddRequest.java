package cn.codeyang.course.dto.classinfo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class ClassInfoAddRequest implements Serializable {
    @NotEmpty(message = "年级不能为空")
    private String name;

    private Integer sort;

    private Long parentId = 0L;
}
