package cn.codeyang.course.service;

import cn.codeyang.course.domain.CourseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CourseTypeService extends IService<CourseType> {
    List<CourseType> selectAll();
}
