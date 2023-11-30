package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.Course;
import cn.codeyang.course.dto.course.CourseAddRequest;
import cn.codeyang.course.dto.course.CourseUpdateRequest;
import cn.codeyang.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 科目设置
 */
@RestController
@RequestMapping("/course/course")
@RequiredArgsConstructor
public class CourseController extends BaseController {
    private final CourseService courseService;



    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @PostMapping("/list")
    public AjaxResult list() {
        List<Course> list = courseService.list();
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('course:course:add')")
    @Log(title = "科目管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody CourseAddRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        return toAjax(courseService.save(course));
    }

    /**
     * 修改教师
     */
    @PreAuthorize("@ss.hasPermi('course:course:edit')")
    @Log(title = "科目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseUpdateRequest request) {
        Course course = new Course();
        course.setId(request.getId());
        course.setName(request.getName());
        return toAjax(courseService.updateById(course));
    }


    /**
     * 删除教师
     */
    @PreAuthorize("@ss.hasPermi('course:course:remove')")
    @Log(title = "科目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(courseService.removeByIds(ids));
    }

}
