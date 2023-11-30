package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.Course;
import cn.codeyang.course.mapper.CourseMapper;
import cn.codeyang.course.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
