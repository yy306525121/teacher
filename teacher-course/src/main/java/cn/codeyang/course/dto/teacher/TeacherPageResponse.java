package cn.codeyang.course.dto.teacher;

import cn.codeyang.course.domain.Subject;
import com.github.yulichang.annotation.EntityMapping;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeacherPageResponse {
    private Long id;

    private String name;

    /**
     * 0-在职
     * 1-离职
     */
    private String status;

    private BigDecimal basicSalary;

    private LocalDateTime createTime;

    private List<Subject> subjectList;
}
