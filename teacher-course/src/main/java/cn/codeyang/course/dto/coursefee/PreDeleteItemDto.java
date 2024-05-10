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
public class PreDeleteItemDto implements Serializable {

    private static final long serialVersionUID = 6981759083480131227L;

    private LocalDate date;
    private Integer week;
    private Long subjectId;
    private List<Long> timeSlotIdList;
    private ClassInfo classInfo;
    List<Teacher> teacherList;
}
