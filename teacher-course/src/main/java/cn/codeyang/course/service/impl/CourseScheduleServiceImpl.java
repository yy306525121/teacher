package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CourseSchedule;
import cn.codeyang.course.dto.courseSchedule.CourseSchedulePageRequest;
import cn.codeyang.course.mapper.CourseScheduleMapper;
import cn.codeyang.course.service.CourseScheduleService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseScheduleServiceImpl extends ServiceImpl<CourseScheduleMapper, CourseSchedule> implements CourseScheduleService {
    @Override
    public List<CourseSchedule> listByName(String name) {
        return this.baseMapper.selectList(Wrappers.<CourseSchedule>lambdaQuery()
                .eq(CourseSchedule::getName, name)
                .orderByDesc(CourseSchedule::getCreateTime));
    }

    @Override
    public int saveCourseSchedule(CourseSchedulePageRequest request) {
        CourseSchedule courseSchedule = BeanUtil.copyProperties(request, CourseSchedule.class);
        return this.baseMapper.insert(courseSchedule);
    }
}
