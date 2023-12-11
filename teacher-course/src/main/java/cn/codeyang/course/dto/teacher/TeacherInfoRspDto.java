package cn.codeyang.course.dto.teacher;

import cn.codeyang.common.jackson.BigDecimalSerializable;
import cn.codeyang.course.dto.subject.SubjectInfoRspDto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TeacherInfoRspDto implements Serializable {
    private static final long serialVersionUID = 3248725213706702752L;

    private Long id;

    private String name;

    /**
     * 0-在职
     * 1-离职
     */
    private String status;

    /**
     * 基础工资
     */
    @JsonSerialize(using = BigDecimalSerializable.class)
    private BigDecimal basicSalary;

    private List<SubjectInfoRspDto> subjectList;
}
