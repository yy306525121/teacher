package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.core.domain.entity.SysUser;
import cn.codeyang.common.core.page.TableDataInfo;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.CourseFeeRule;
import cn.codeyang.course.dto.feeRule.CourseFeeRuleAddRequest;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageRequest;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageResponse;
import cn.codeyang.course.service.CourseFeeRuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/course/feeRule")
@RequiredArgsConstructor
public class CourseFeeRuleController extends BaseController {
    private final CourseFeeRuleService courseFeeRuleService;

    @PreAuthorize("@ss.hasPermi('course:fee:calculate')")
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
    @PreAuthorize("@ss.hasPermi('system:rule:edit')")
    @Log(title = "课时费计算规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseFeeRule courseFeeRule) {
        return toAjax(courseFeeRuleService.updateById(courseFeeRule));
    }
}
