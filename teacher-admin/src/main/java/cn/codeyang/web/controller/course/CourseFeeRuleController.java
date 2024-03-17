package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.CourseFeeRule;
import cn.codeyang.course.dto.feeRule.CourseFeeRuleAddRequest;
import cn.codeyang.course.service.CourseFeeRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/feeRule")
@RequiredArgsConstructor
public class CourseFeeRuleController extends BaseController {
    private final CourseFeeRuleService courseFeeRuleService;

    /**
     * 新增课时费计算规则
     */
    @PreAuthorize("@ss.hasPermi('course:feeRule:add')")
    @Log(title = "课时费计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody CourseFeeRuleAddRequest request) {
        CourseFeeRule courseFeeRule = new CourseFeeRule();
        courseFeeRule.setType(request.getType());

        if (request.getType() == 1 || request.getType() == 2) {
            // 放假 | 考试
            if (request.getStartDate() == null) {
                return AjaxResult.error("开始日期不能为空");
            }
            if (request.getStartTimeSlotId() == null) {
                return AjaxResult.error("开始课程节次不能为空");
            }
            if (request.getEndDate() == null) {
                return AjaxResult.error("结束日期不能为空");
            }
            if (request.getEndTimeSlotId() == null) {
                return AjaxResult.error("结束课程节次不能为空");
            }
            courseFeeRule.setStartDate(request.getStartDate());
            courseFeeRule.setStartTimeSlotId(request.getStartTimeSlotId());
            courseFeeRule.setEndDate(request.getEndDate());
            courseFeeRule.setEndTimeSlotId(request.getEndTimeSlotId());

            if (request.getClassInfoId() != null) {
                courseFeeRule.setClassInfoId(request.getClassInfoId());
            }
        } else if (request.getType() == 3) {
            // 课程调整
            if (request.getClassInfoId() == null) {
                return AjaxResult.error("班级不能为空");
            }
            if (request.getOverrideDate() == null) {
                return AjaxResult.error("日期不能为空");
            }
            if (request.getOverrideTimeSlotId() == null) {
                return AjaxResult.error("课程节次不能为空");
            }
            if (request.getOverrideTeacherId() == null) {
                return AjaxResult.error("教师不能为空");
            }
            if (request.getOverrideCourseTypeId() == null) {
                return AjaxResult.error("课程类型不能为空");
            }
            if (request.getOverrideCourseTypeId() == 1 && request.getOverrideSubjectId() == null) {
                return AjaxResult.error("当调课类型为正常课程时科目不能为空");
            }
            courseFeeRule.setClassInfoId(request.getClassInfoId());
            courseFeeRule.setOverrideDate(request.getOverrideDate());
            courseFeeRule.setOverrideTimeSlotId(request.getOverrideTimeSlotId());
            courseFeeRule.setOverrideTeacherId(request.getOverrideTeacherId());
            courseFeeRule.setOverrideCourseTypeId(request.getOverrideCourseTypeId());
            if (request.getOverrideCourseTypeId() == 1) {
                courseFeeRule.setOverrideSubjectId(request.getOverrideSubjectId());
            }
        }

        return toAjax(courseFeeRuleService.save(courseFeeRule));
    }


    /**
     * 修改课时费计算规则
     */
    @PreAuthorize("@ss.hasPermi('system:rule:edit')")
    @Log(title = "课时费计算规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseFeeRule courseFeeRule) {
        return toAjax(courseFeeRuleService.updateById(courseFeeRule));
    }
}
