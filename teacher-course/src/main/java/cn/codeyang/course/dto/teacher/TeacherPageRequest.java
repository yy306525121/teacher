package cn.codeyang.course.dto.teacher;

import cn.codeyang.common.core.dto.RequestTemplate;
import cn.codeyang.course.domain.Teacher;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherPageRequest extends RequestTemplate<Teacher> {
    private String name;

    /**
     * 0-在职
     * 1-离职
     */
    private String status;
}
