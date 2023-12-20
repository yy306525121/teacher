package cn.codeyang.course.dto.courseplan;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePlanListRspDto implements Serializable {
    private Long id;

    private Long classInfoId;

    private String classInfoName;

    private Long teacherId;

    private String teacherName;

    private Long subjectId;

    private String subjectName;

    private Integer week;

    private Integer numInDay;
}
