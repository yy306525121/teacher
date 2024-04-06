package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.mapper.CourseTypeMapper;
import cn.codeyang.course.service.CourseTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements CourseTypeService {
}
