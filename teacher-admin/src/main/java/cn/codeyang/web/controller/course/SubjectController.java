package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.core.page.TableDataInfo;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.dto.subject.SubjectAddRequest;
import cn.codeyang.course.dto.subject.SubjectListRequest;
import cn.codeyang.course.dto.subject.SubjectPageRequest;
import cn.codeyang.course.dto.subject.SubjectUpdateRequest;
import cn.codeyang.course.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 科目设置
 */
@RestController
@RequestMapping("/course/subject")
@RequiredArgsConstructor
public class SubjectController extends BaseController {
    private final SubjectService subjectService;



    @PreAuthorize("@ss.hasPermi('course:subject:list')")
    @PostMapping("/page")
    public TableDataInfo page(@RequestBody SubjectPageRequest request) {
        startPage();
        List<Subject> list = subjectService.list(request.getName());
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('course:subject:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody SubjectListRequest request) {
        List<Subject> list = subjectService.list(request.getName());
        return success(list);
    }



    @PreAuthorize("@ss.hasPermi('course:subject:add')")
    @Log(title = "科目管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody SubjectAddRequest request) {
        Subject course = new Subject();
        course.setName(request.getName());
        course.setSort(request.getSort());
        return toAjax(subjectService.save(course));
    }

    /**
     * 修改科目
     */
    @PreAuthorize("@ss.hasPermi('course:subject:edit')")
    @Log(title = "科目管理", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SubjectUpdateRequest request) {
        Subject course = new Subject();
        course.setId(request.getId());
        course.setName(request.getName());
        course.setSort(request.getSort());
        return toAjax(subjectService.updateById(course));
    }

    @PreAuthorize("@ss.hasPermi('course:subject:list')")
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        return AjaxResult.success(subjectService.getById(id));
    }

    /**
     * 删除科目
     */
    @PreAuthorize("@ss.hasPermi('course:subject:remove')")
    @Log(title = "科目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids) {
        return toAjax(subjectService.removeByIds(ids));
    }

}
