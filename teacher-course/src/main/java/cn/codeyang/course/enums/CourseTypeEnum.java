package cn.codeyang.course.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yangzy
 */
@Getter
@RequiredArgsConstructor
public enum CourseTypeEnum {
    NORMAL(1, "正常课时"),
    STUDY_SELF(2, "自习课"),
    MORNING(3, "早自习"),
    EVENING(4, "晚自习");


    /**
     * 状态值
     */
    private final Integer type;
    /**
     * 状态名
     */
    private final String name;
}
