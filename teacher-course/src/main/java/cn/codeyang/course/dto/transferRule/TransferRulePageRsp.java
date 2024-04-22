package cn.codeyang.course.dto.transferRule;

import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.domain.TimeSlot;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TransferRulePageRsp implements Serializable {
    private Long id;

    /**
     * 调课日期
     */
    private LocalDate overrideDate;

    /**
     * 调课节次
     */
    private Long overrideTimeSlotId;

    private TimeSlot overrideTimeSlot;

    /**
     * 调课前教师
     */
    private Long overrideFromTeacherId;

    private Teacher overrideFromTeacher;

    /**
     * 调课后教师
     */
    private Long overrideToTeacherId;

    private Teacher overrideToTeacher;

    /**
     * 调课前课程
     */
    private Long overrideFromSubjectId;

    private Subject overrideFromSubject;

    /**
     * 调课后课程
     */
    private Long overrideToSubjectId;

    private Subject overrideToSubject;

    /**
     * 调整前的课程类型
     */
    private Long overrideFromCourseTypeId;

    private CourseType overrideFromCourseType;

    /**
     * 调整后的课程类型
     */
    private Long overrideToCourseTypeId;

    private CourseType overrideToCourseType;
}
