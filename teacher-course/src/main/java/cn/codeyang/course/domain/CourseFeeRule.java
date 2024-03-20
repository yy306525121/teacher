package cn.codeyang.course.domain;

import cn.codeyang.common.annotation.Excel;
import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_fee_rule")
public class CourseFeeRule extends BaseEntity {
    private Long id;


    /** 规则类型, 1-放假时间规则、2-考试规则、3-调课规则 */
    @Excel(name = "规则类型, 1-上课时间规则、2-放假时间规则、3-调课规则、4-考试规则")
    private Integer type;

    /** 班级ID */
    @Excel(name = "班级ID")
    private String classInfoId;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDate startDate;

    /** 开始课时 */
    @Excel(name = "开始课时")
    private Long startTimeSlotId;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private LocalDate endDate;

    /** 结束课时 */
    @Excel(name = "结束课时")
    private Long endTimeSlotId;

    /** 教师ID */
    @Excel(name = "教师ID")
    private LocalDate overrideDate;

    /** 教师ID */
    @Excel(name = "教师ID")
    private Long overrideTimeSlotId;

    private Long overrideTeacherId;

    private Long overrideSubjectId;

    private Long overrideCourseTypeId;
}
