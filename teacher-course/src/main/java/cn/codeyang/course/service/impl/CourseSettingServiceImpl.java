package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CourseSetting;
import cn.codeyang.course.mapper.CourseSettingMapper;
import cn.codeyang.course.service.CourseSettingService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author yangzy
 */
@Service
public class CourseSettingServiceImpl extends ServiceImpl<CourseSettingMapper, CourseSetting> implements CourseSettingService {
    @Override
    public CourseSetting getCurrent() {
        return this.baseMapper.selectOne(Wrappers.<CourseSetting>lambdaQuery()
                .orderByDesc(CourseSetting::getUpdateTime)
                .last("limit 1"));
    }
}
