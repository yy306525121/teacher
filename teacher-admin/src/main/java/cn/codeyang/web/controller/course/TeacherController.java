package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.core.page.TableDataInfo;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/course/teacher")
@RequiredArgsConstructor
public class TeacherController extends BaseController {
    private final TeacherService teacherService;


    @PreAuthorize("@ss.hasPermi('course:teacher:page')")
    @PostMapping("/page")
    public TableDataInfo page() {
        startPage();
        List<Teacher> list = teacherService.list();
        return getDataTable(list);
    }


    /**
     * 新增教师
     */
    @PreAuthorize("@ss.hasPermi('course:teacher:add')")
    @Log(title = "教师", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Teacher teacher) {
        return toAjax(teacherService.save(teacher));
    }

    /**
     * 修改教师
     */
    @PreAuthorize("@ss.hasPermi('course:teacher:edit')")
    @Log(title = "教师", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Teacher teacher) {
        return toAjax(teacherService.updateById(teacher));
    }

    /**
     * 删除教师
     */
    @PreAuthorize("@ss.hasPermi('course:teacher:remove')")
    @Log(title = "教师", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(teacherService.removeByIds(Arrays.asList(ids)));
    }
}
