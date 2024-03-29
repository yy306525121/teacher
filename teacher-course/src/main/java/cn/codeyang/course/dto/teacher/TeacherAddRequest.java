package cn.codeyang.course.dto.teacher;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TeacherAddRequest implements Serializable {

    private static final long serialVersionUID = 1872058277200089131L;

    @NotEmpty(message = "教师姓名不能为空")
    private String name;
    @NotEmpty(message = "教师在职状态不能为空")
    private String status;
    @NotNull(message = "教师基本工资不能为空")
    private BigDecimal basicSalary;

    private List<Long> subjectIds;
}
