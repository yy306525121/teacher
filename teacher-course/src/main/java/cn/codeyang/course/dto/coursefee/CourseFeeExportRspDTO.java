package cn.codeyang.course.dto.coursefee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author yangzy
 */
@Data
public class CourseFeeExportRspDTO implements Serializable {
    private LocalDate date;

    private BigDecimal count;

    private String className;

    private String teacherName;

}
