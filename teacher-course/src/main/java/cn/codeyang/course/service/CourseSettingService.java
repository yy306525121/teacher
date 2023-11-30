package cn.codeyang.course.service;

import cn.codeyang.course.domain.CourseSetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author yangzy
 */
public interface CourseSettingService extends IService<CourseSetting> {
    CourseSetting getCurrent();
}
