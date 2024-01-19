package cn.codeyang.course.dto.courseplan;

import lombok.Data;

import java.io.Serializable;

/**
 * 页面显示对象
 */
@Data
public class CoursePlanViewRspDto implements Serializable {
    private CoursePlanDto monday;
    private CoursePlanDto tuesday;
    private CoursePlanDto wednesday;
    private CoursePlanDto thursday;
    private CoursePlanDto friday;
    private CoursePlanDto saturday;
    private CoursePlanDto sunday;
    private Integer numInDay;
    private String time;
}
