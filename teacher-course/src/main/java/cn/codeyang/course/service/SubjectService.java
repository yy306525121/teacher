package cn.codeyang.course.service;

import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.dto.subject.SubjectPageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SubjectService extends IService<Subject> {
    List<Subject> list(String name);


    Subject getByName(String name);

    /**
     * 获取课程信息，如果不存在就创建
     * @param name
     * @return
     */
    Subject getByNameAndCreate(String name);
}
