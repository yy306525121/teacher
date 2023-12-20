package cn.codeyang.course.dto.courseplan;

import lombok.Data;

import java.io.Serializable;

/**
 * 页面显示对象
 */
@Data
public class CoursePlanViewRspDto implements Serializable {
    private CoursePlanListRspDto monday;
    private CoursePlanListRspDto tuesday;
    private CoursePlanListRspDto wednesday;
    private CoursePlanListRspDto thursday;
    private CoursePlanListRspDto friday;
    private CoursePlanListRspDto saturday;
    private CoursePlanListRspDto sunday;
    private Integer numInDay;
    private String time;
}
