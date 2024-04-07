package cn.codeyang.course.dto.coursefee;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yangzy
 */
@Data
public class CourseFeeExportReqDTO implements Serializable {
    @NotNull(message = "月份不能为空")
    private LocalDate date;
}
