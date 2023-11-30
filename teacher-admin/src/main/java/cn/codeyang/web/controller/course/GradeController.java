package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.Grade;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.grade.GradeAddRequest;
import cn.codeyang.course.dto.grade.GradeUpdateRequest;
import cn.codeyang.course.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 年级班级
 */
@RestController
@RequestMapping("/course/grade")
@RequiredArgsConstructor
public class GradeController extends BaseController {
    private final GradeService gradeService;

    @PreAuthorize("@ss.hasPermi('course:grade:list')")
    @PostMapping("/list")
    public AjaxResult list() {
        List<Grade> list = gradeService.listOrderByGrade();
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('course:grade:list')")
    @PostMapping("/tree")
    public AjaxResult tree() {
        return success(gradeService.tree());
    }

    @PreAuthorize("@ss.hasPermi('course:grade:add')")
    @Log(title = "班级管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody GradeAddRequest request) {
        Grade grade = new Grade();
        grade.setGradeName(request.getGradeName());
        grade.setParentId(request.getParentId());
        return toAjax(gradeService.save(grade));
    }

    /**
     * 修改教师
     */
    @PreAuthorize("@ss.hasPermi('course:grade:edit')")
    @Log(title = "班级管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GradeUpdateRequest request) {
        Grade grade = new Grade();
        grade.setId(request.getId());
        grade.setGradeName(request.getGradeName());
        return toAjax(gradeService.updateById(grade));
    }


    /**
     * 删除教师
     */
    @PreAuthorize("@ss.hasPermi('course:grade:remove')")
    @Log(title = "班级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(gradeService.removeByIds(ids));
    }


}
