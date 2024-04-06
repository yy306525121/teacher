package cn.codeyang.course.constant;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CourseConstant {
    /**
     * 1, 3, 5早自习语文
     */
    public static final List<Integer> chineseMorningEarlyWeek = Arrays.asList(1, 3, 5);

    /**
     * 2，4，6早自习英语
     */
    public static final List<Integer> englishMorningEarlyWeek = Arrays.asList(2, 4, 6);

    public static final String SUBJECT_CHINESE = "语文";

    public static final String SUBJECT_ENGLISH = "英语";

    /**
     * 早自习课时单位
     */
    public static final BigDecimal MORNING_EARLY_FEE = new BigDecimal("0.5");

    /**
     * 平时自习课单位
     */
    public static final BigDecimal SELF_FEE = new BigDecimal("0.5");
    /**
     * 正常课时
     */
    public static final BigDecimal NORMAL_FEE = new BigDecimal("1");

    /**
     * 早自习的courseType值
     */
    public static final Integer COURSE_TYPE_MORNING = 3;

    /**
     * timeSlot 早自习type值
     */
    public static final Integer TIME_SLOT_TYPE_MORNING = 1;
}
