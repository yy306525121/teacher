package cn.codeyang.course.dto.subject;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectListRequest implements Serializable {
    private static final long serialVersionUID = 7257222536132042809L;
    private String name;
}
