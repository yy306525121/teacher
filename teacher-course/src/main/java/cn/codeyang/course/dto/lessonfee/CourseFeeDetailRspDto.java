package cn.codeyang.course.dto.lessonfee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CourseFeeDetailRspDto implements Serializable {
    private LocalDate date;

    private BigDecimal count;
}
