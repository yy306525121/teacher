package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.core.page.TableDataInfo;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.CourseSetting;
import cn.codeyang.course.service.CourseSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangzy
 */
@RestController
@RequestMapping("/course/course-setting")
@RequiredArgsConstructor
public class CourseSettingController extends BaseController {
    private final CourseSettingService courseSettingService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('course:course-setting:list')")
    @GetMapping("/page")
    public TableDataInfo page() {
        startPage();
        List<CourseSetting> list = courseSettingService.list();
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('course:course-setting:list')")
    @GetMapping("/current")
    public AjaxResult current() {
        return success(courseSettingService.getCurrent());
    }

    /**
     * 获取课程基础设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:course-setting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(courseSettingService.getById(id));
    }

    /**
     * 新增课程基础设置
     */
    @PreAuthorize("@ss.hasPermi('course:course-setting:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseSetting courseSetting) {

        return toAjax(courseSettingService.save(courseSetting));
    }

    /**
     * 修改课程基础设置
     */
    @PreAuthorize("@ss.hasPermi('system:setting:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseSetting courseSetting) {
        return toAjax(courseSettingService.updateById(courseSetting));
    }

    /**
     * 删除课程基础设置
     */
    @PreAuthorize("@ss.hasPermi('system:setting:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(courseSettingService.removeByIds(ids));
    }
}
