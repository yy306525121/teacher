package cn.codeyang.course.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author yangzy
 */
@RequiredArgsConstructor
@Getter
public enum CourseFeeRuleTypeEnum {
    HOLIDAY(1, "放假规则"),
    EXAM(2, "考试规则"),
    CHANGE(3, "调课规则");

    /**
     * 状态值
     */
    private final Integer type;
    /**
     * 状态名
     */
    private final String name;
}
