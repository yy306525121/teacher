package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.*;
import cn.codeyang.course.dto.transferRule.TransferRuleAddRequest;
import cn.codeyang.course.dto.transferRule.TransferRuleEditRequest;
import cn.codeyang.course.dto.transferRule.TransferRulePageRequest;
import cn.codeyang.course.dto.transferRule.TransferRulePageRsp;
import cn.codeyang.course.service.*;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course/transferRule")
@RequiredArgsConstructor
public class TransferRuleController extends BaseController {
    private final TransferRuleService transferRuleService;
    private final TimeSlotService timeSlotService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final CourseTypeService courseTypeService;

    @PreAuthorize("@ss.hasPermi('course:transferRule:list')")
    @PostMapping("/page")
    public AjaxResult page(@RequestBody TransferRulePageRequest request) {
        IPage<TransferRule> result = transferRuleService.selectPageList(request);
        List<TransferRule> sourceRecords = result.getRecords();
        List<TransferRulePageRsp> targetRecords = new ArrayList<>();

        List<TimeSlot> timeSlotList = timeSlotService.list();
        List<Teacher> teacherList = teacherService.list();
        List<Subject> subjectList = subjectService.list();
        List<CourseType> courseTypeList = courseTypeService.list();


        for (TransferRule sourceRecord : sourceRecords) {
            TransferRulePageRsp targetRecord = BeanUtil.copyProperties(sourceRecord, TransferRulePageRsp.class);

            TimeSlot overrideTimeSlot = timeSlotList.stream().filter(item -> item.getId().equals(sourceRecord.getOverrideTimeSlotId())).findFirst().orElse(null);
            Teacher overrideFromTeacher = teacherList.stream().filter(item -> item.getId().equals(sourceRecord.getOverrideFromTeacherId())).findFirst().orElse(null);
            Teacher overrideToTeacher = teacherList.stream().filter(item -> item.getId().equals(sourceRecord.getOverrideToTeacherId())).findFirst().orElse(null);

            Subject overrideFromSubject = subjectList.stream().filter(item -> item.getId().equals(sourceRecord.getOverrideFromSubjectId())).findFirst().orElse(null);
            Subject overrideToSubject = subjectList.stream().filter(item -> item.getId().equals(sourceRecord.getOverrideToSubjectId())).findFirst().orElse(null);

            CourseType overrideFromCourseType = courseTypeList.stream().filter(item -> item.getId().equals(sourceRecord.getOverrideFromCourseTypeId())).findFirst().orElse(null);
            CourseType overrideToCourseType = courseTypeList.stream().filter(item -> item.getId().equals(sourceRecord.getOverrideToCourseTypeId())).findFirst().orElse(null);


            targetRecord.setOverrideTimeSlot(overrideTimeSlot);
            targetRecord.setOverrideFromTeacher(overrideFromTeacher);
            targetRecord.setOverrideToTeacher(overrideToTeacher);
            targetRecord.setOverrideFromSubject(overrideFromSubject);
            targetRecord.setOverrideToSubject(overrideToSubject);
            targetRecord.setOverrideFromCourseType(overrideFromCourseType);
            targetRecord.setOverrideToCourseType(overrideToCourseType);

            targetRecords.add(targetRecord);
        }


        IPage<TransferRulePageRsp> pageResult = new Page<>();
        pageResult.setCurrent(result.getCurrent());
        pageResult.setPages(result.getPages());
        pageResult.setSize(result.getSize());
        pageResult.setTotal(result.getTotal());
        pageResult.setRecords(targetRecords);
        return success(pageResult);
    }

    @PreAuthorize("@ss.hasPermi('course:transferRule:list')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        return AjaxResult.success(transferRuleService.getById(id));
    }

    /**
     * 新增课时费计算规则
     */
    @PreAuthorize("@ss.hasPermi('course:transferRule:add')")
    @Log(title = "课时费计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody TransferRuleAddRequest request) {
        TransferRule rule = BeanUtil.copyProperties(request, TransferRule.class);
        return toAjax(transferRuleService.save(rule));
    }


    @PreAuthorize("@ss.hasPermi('course:transferRule:edit')")
    @Log(title = "课时费计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    public AjaxResult edit(@Valid @RequestBody TransferRuleEditRequest request) {

        TransferRule rule = BeanUtil.copyProperties(request, TransferRule.class);
        return toAjax(transferRuleService.updateById(rule));
    }


    @PreAuthorize("@ss.hasPermi('course:examRule:remove')")
    @Log(title = "放假规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable(value = "ids") List<String> ids) {
        return toAjax(transferRuleService.removeByIds(ids));
    }
}
