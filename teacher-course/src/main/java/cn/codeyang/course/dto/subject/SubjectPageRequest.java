package cn.codeyang.course.dto.subject;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectPageRequest implements Serializable {
    private String name;
}
