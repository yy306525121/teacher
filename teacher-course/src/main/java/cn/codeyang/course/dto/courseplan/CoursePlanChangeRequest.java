package cn.codeyang.course.dto.courseplan;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yangzy
 */
@Data
public class CoursePlanChangeRequest implements Serializable {
    /**
     * 换课班级
     */
    private Long classInfoId;

    /**
     * 换课开始日期
     */
    private LocalDate date;

    /**
     * 原有老师
     */
    private Long fromTeacherId;

    /**
     * 替换老师
     */
    private Long toTeacherId;

    private Long fromSubjectId;
    private Long toSubjectId;
}
