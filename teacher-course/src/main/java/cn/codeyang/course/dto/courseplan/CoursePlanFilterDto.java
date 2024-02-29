package cn.codeyang.course.dto.courseplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePlanFilterDto implements Serializable {
    private static final long serialVersionUID = -2045176243165004538L;

    /**
     * 班级
     */
    private Long classInfoId;

    /**
     * 从第几节课开始上课
     */
    private Integer sortOfDayStart;
}
