package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.mapper.TeacherMapper;
import cn.codeyang.course.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
