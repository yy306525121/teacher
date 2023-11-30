package cn.codeyang.course.domain;

import cn.codeyang.common.annotation.Excel;
import cn.codeyang.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程设置
 * @author yangzy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_setting")
public class CourseSetting extends BaseEntity {
    /** 教师ID */
    private Long id;

    /** 每周上几天课 */
    @Excel(name = "每周上几天课")
    private Long dayOfPerWeek;

    /** 早自习几节课 */
    @Excel(name = "早自习几节课")
    private Long sizeOfMorningEarly;

    /** 上午几节课 */
    @Excel(name = "上午几节课")
    private Long sizeOfMorning;

    /** 下午几节课 */
    @Excel(name = "下午几节课")
    private Long sizeOfAfternoon;

    /** 晚自习几节课 */
    @Excel(name = "晚自习几节课")
    private Long sizeOfNight;
}
