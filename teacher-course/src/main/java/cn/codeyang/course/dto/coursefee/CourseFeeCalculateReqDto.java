package cn.codeyang.course.dto.coursefee;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CourseFeeCalculateReqDto implements Serializable {
    private static final long serialVersionUID = 6251200270835837462L;

    private Long teacherId;

    @NotNull(message = "月份不能为空")
    private LocalDate date;
}
