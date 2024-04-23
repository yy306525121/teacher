package cn.codeyang.course.dto.coursefee;

import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.TimeSlot;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author yangzy
 */
@Data
public class IgnoreItemDto implements Serializable {
    private static final long serialVersionUID = 5329699105864030358L;

    private LocalDate date;

    private TimeSlot timeSlot;

    private ClassInfo classInfo;
}
