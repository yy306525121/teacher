package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.core.page.TableDataInfo;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.CourseFeeRule;
import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.dto.feeRule.CourseFeeRuleAddRequest;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageResponse;
import cn.codeyang.course.service.CourseFeeRuleService;
import cn.codeyang.course.service.TimeSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/course/feeRule")
@RequiredArgsConstructor
public class CourseFeeRuleController extends BaseController {
    private final CourseFeeRuleService courseFeeRuleService;
    private final TimeSlotService timeSlotService;

    @PreAuthorize("@ss.hasPermi('course:feeRule:list')")
    @GetMapping("/page")
    public TableDataInfo page(@RequestParam(value = "date", required = false) LocalDate date) {
        startPage();
        List<CourseFeeRulePageResponse> list = courseFeeRuleService.selectFeeRuleList(date);
        return getDataTable(list);
    }

    /**
     * 新增课时费计算规则
     */
    @PreAuthorize("@ss.hasPermi('course:feeRule:add')")
    @Log(title = "课时费计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody CourseFeeRuleAddRequest request) {
        CourseFeeRule courseFeeRule = new CourseFeeRule();
        courseFeeRule.setType(request.getType());

        if (request.getStartDate() == null && request.getEndDate() == null) {
            return error("开始日期和结束日期不能同时为空");
        }
        if (request.getStartDate() != null && request.getStartTimeSlotId() == null) {
            return error("开始课程节次不能为空");
        }
        if (request.getEndDate() != null && request.getEndTimeSlotId() == null) {
            return error("结束课程节次不能为空");
        }

        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getStartDate().isAfter(request.getEndDate())) {
                return error("开始日期不能大于结束日期");
            }

            if (request.getStartDate().equals(request.getEndDate())) {
                TimeSlot startTimeSlot = timeSlotService.getById(request.getStartTimeSlotId());
                TimeSlot endTimeSlot = timeSlotService.getById(request.getEndTimeSlotId());
                if (endTimeSlot.getSortInDay() <= startTimeSlot.getSortInDay()) {
                    return error("结束课程节次必须大于开始课程节次");
                }
            }
        }

        courseFeeRule.setStartDate(request.getStartDate());
        courseFeeRule.setStartTimeSlotId(request.getStartTimeSlotId());
        courseFeeRule.setEndDate(request.getEndDate());
        courseFeeRule.setEndTimeSlotId(request.getEndTimeSlotId());
        if (request.getClassInfoId() != null) {
            courseFeeRule.setClassInfoId(request.getClassInfoId());
        }

        return toAjax(courseFeeRuleService.save(courseFeeRule));
    }


    /**
     * 修改课时费计算规则
     */
    @PreAuthorize("@ss.hasPermi('system:feeRule:edit')")
    @Log(title = "课时费计算规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseFeeRule courseFeeRule) {
        if (courseFeeRule.getStartDate() == null && courseFeeRule.getEndDate() == null) {
            return error("开始日期和结束日期不能同时为空");
        }
        if (courseFeeRule.getStartDate() != null && courseFeeRule.getStartTimeSlotId() == null) {
            return error("开始课程节次不能为空");
        }
        if (courseFeeRule.getEndDate() != null && courseFeeRule.getEndTimeSlotId() == null) {
            return error("结束课程节次不能为空");
        }

        if (courseFeeRule.getStartDate() != null && courseFeeRule.getEndDate() != null) {
            if (courseFeeRule.getStartDate().isAfter(courseFeeRule.getEndDate())) {
                return error("开始日期不能大于结束日期");
            }

            if (courseFeeRule.getStartDate().equals(courseFeeRule.getEndDate())) {
                TimeSlot startTimeSlot = timeSlotService.getById(courseFeeRule.getStartTimeSlotId());
                TimeSlot endTimeSlot = timeSlotService.getById(courseFeeRule.getEndTimeSlotId());
                if (endTimeSlot.getSortInDay() <= startTimeSlot.getSortInDay()) {
                    return error("结束课程节次必须大于开始课程节次");
                }
            }
        }
        return toAjax(courseFeeRuleService.updateById(courseFeeRule));
    }

    @PreAuthorize("@ss.hasPermi('course:feeRule:list')")
    @GetMapping(value = {"/", "/{id}"})
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        return success(courseFeeRuleService.getById(id));
    }

    @PreAuthorize("@ss.hasPermi('course:feeRule:remove')")
    @Log(title = "课时费计算规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable(value = "ids") Long[] ids) {
        return toAjax(courseFeeRuleService.removeByIds(Arrays.asList(ids)));
    }
}
