package cn.codeyang.course.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yangzy
 */
@Getter
@RequiredArgsConstructor
public enum TimeSlotTypeEnum {
    MORNING(1, "早自习"),
    EVENING(2, "晚自习"),
    NORMAL(3,  "正常课时");

    /**
     * 状态值
     */
    private final Integer type;
    /**
     * 状态名
     */
    private final String name;
}
