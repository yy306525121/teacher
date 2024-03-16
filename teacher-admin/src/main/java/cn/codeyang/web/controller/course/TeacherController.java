package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.core.page.TableDataInfo;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.teacher.TeacherAddRequest;
import cn.codeyang.course.dto.teacher.TeacherPageRequest;
import cn.codeyang.course.dto.teacher.TeacherPageResponse;
import cn.codeyang.course.dto.teacher.TeacherUpdateRequest;
import cn.codeyang.course.service.TeacherService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
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


    @PreAuthorize("@ss.hasPermi('course:teacher:list')")
    @PostMapping("/page")
    public AjaxResult page(@RequestBody TeacherPageRequest request) {
        IPage<Teacher> result = teacherService.selectPageList(request);
        return success(result);
    }


    /**
     * 新增教师
     */
    @PreAuthorize("@ss.hasPermi('course:teacher:add')")
    @Log(title = "教师", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody TeacherAddRequest request) {
        return toAjax(teacherService.saveTeacher(request));
    }

    /**
     * 修改教师
     */
    @PreAuthorize("@ss.hasPermi('course:teacher:edit')")
    @Log(title = "教师", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody TeacherUpdateRequest request) {
        return toAjax(teacherService.updateTeacher(request));
    }

    @PreAuthorize("@ss.hasPermi('course.teacher.list')")
    @GetMapping("/info/{id}")
    public AjaxResult info(@PathVariable(value = "id") Long id) {
        return AjaxResult.success(teacherService.getInfo(id));
    }

    /**
     * 删除教师
     */
    @PreAuthorize("@ss.hasPermi('course:teacher:remove')")
    @Log(title = "教师", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable(value = "ids") Long[] ids) {
        return toAjax(teacherService.removeByIds(Arrays.asList(ids)));
    }
}
