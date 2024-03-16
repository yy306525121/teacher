package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.page.TableDataInfo;
import cn.codeyang.course.domain.CourseSchedule;
import cn.codeyang.course.dto.courseSchedule.CourseSchedulePageRequest;
import cn.codeyang.course.service.CourseScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/course/courseSchedule")
@RequiredArgsConstructor
public class CourseScheduleController extends BaseController {
    private final CourseScheduleService courseScheduleService;

    @PreAuthorize("@ss.hasPermi('course:courseSchedule:list')")
    @PostMapping("/page")
    public TableDataInfo page(@RequestBody CourseSchedulePageRequest request) {
        startPage();
        List<CourseSchedule> list = courseScheduleService.listByName(request.getName());
        return getDataTable(list);
    }
}
