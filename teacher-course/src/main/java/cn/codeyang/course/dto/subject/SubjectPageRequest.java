package cn.codeyang.course.dto.subject;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectPageRequest implements Serializable {

    private static final long serialVersionUID = 2205754459174150589L;
    private String name;
}
