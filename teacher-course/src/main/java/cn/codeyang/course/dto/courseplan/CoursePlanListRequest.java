package cn.codeyang.course.dto.courseplan;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePlanListRequest implements Serializable {
    private Long classInfoId;
    private Long teacherId;
    /**
     *
     */
    private Integer queryType;
}
