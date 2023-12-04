package cn.codeyang.course.dto.classinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClassInfoListRequest implements Serializable {
    private Long parentId;
}
