package cn.codeyang.course.dto.subject;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectInfoRspDto implements Serializable {
    private static final long serialVersionUID = 5198579557299875040L;

    private Long id;
    private String name;
    private Integer sort;
}
