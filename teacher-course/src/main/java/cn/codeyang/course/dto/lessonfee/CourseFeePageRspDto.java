package cn.codeyang.course.dto.lessonfee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseFeePageRspDto implements Serializable {
    private Long teacherId;

    private String teacherName;

    /**
     * 课时总数
     */
    private BigDecimal lessonCount;


}
