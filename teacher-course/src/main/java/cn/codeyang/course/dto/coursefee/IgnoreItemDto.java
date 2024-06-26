package cn.codeyang.course.dto.coursefee;

import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.domain.TimeSlot;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
