package cn.codeyang.course.dto.lessonfee;

import cn.codeyang.common.core.dto.RequestTemplate;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CourseFeePageReqDto extends RequestTemplate<CourseFeePageRspDto> {
    private static final long serialVersionUID = -8887387577568985111L;

    @NotNull(message = "月份不能为空")
    private LocalDate date;
}
