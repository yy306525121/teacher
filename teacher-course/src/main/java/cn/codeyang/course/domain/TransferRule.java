package cn.codeyang.course.domain;

import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_transfer_rule")
public class TransferRule extends BaseEntity {
    private Long id;

    /**
     * 调课日期
     */
    private LocalDate overrideDate;

    /**
     * 调课节次
     */
    private Long overrideTimeSlotId;

    /**
     * 调课前教师
     */
    private Long overrideFromTeacherId;

    /**
     * 调课后教师
     */
    private Long overrideToTeacherId;

    /**
     * 调课前课程
     */
    private Long overrideFromSubjectId;

    /**
     * 调课后课程
     */
    private Long overrideToSubjectId;

    /**
     * 调整前的课程类型
     */
    private Long overrideFromCourseTypeId;

    /**
     * 调整后的课程类型
     */
    private Long overrideToCourseTypeId;
}
