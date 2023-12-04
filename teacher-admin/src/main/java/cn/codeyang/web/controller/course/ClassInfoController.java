package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.dto.classinfo.ClassInfoAddRequest;
import cn.codeyang.course.dto.classinfo.ClassInfoListRequest;
import cn.codeyang.course.dto.classinfo.ClassInfoUpdateRequest;
import cn.codeyang.course.service.ClassInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 年级班级
 */
@RestController
@RequestMapping("/course/class")
@RequiredArgsConstructor
public class ClassInfoController extends BaseController {
    private final ClassInfoService classInfoService;

    @PreAuthorize("@ss.hasPermi('course:class:list')")
    @PostMapping("/list")
    public AjaxResult list(@Valid @RequestBody ClassInfoListRequest request) {
        List<ClassInfo> list = classInfoService.list(request);
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('course:class:list')")
    @PostMapping("/tree")
    public AjaxResult tree() {
        return success(classInfoService.tree());
    }

    @PreAuthorize("@ss.hasPermi('course:class:add')")
    @Log(title = "班级管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody ClassInfoAddRequest request) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setName(request.getName());
        classInfo.setSort(request.getSort());
        classInfo.setParentId(request.getParentId());
        return toAjax(classInfoService.save(classInfo));
    }

    /**
     * 修改教师
     */
    @PreAuthorize("@ss.hasPermi('course:class:edit')")
    @Log(title = "班级管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ClassInfoUpdateRequest request) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setId(request.getId());
        classInfo.setName(request.getName());
        classInfo.setSort(request.getSort());
        if (request.getParentId() != null) {
            classInfo.setParentId(request.getParentId());
        }
        return toAjax(classInfoService.updateById(classInfo));
    }


    /**
     * 删除教师
     */
    @PreAuthorize("@ss.hasPermi('course:class:remove')")
    @Log(title = "班级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(classInfoService.removeByIds(ids));
    }


}
