package cn.codeyang.course.service;

import cn.codeyang.course.domain.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SubjectService extends IService<Subject> {
    List<Subject> listBySort();

}
