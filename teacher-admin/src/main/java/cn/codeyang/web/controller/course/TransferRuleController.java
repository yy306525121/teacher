package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.TransferRule;
import cn.codeyang.course.dto.transferRule.TransferRuleAddRequest;
import cn.codeyang.course.dto.transferRule.TransferRuleEditRequest;
import cn.codeyang.course.dto.transferRule.TransferRulePageRequest;
import cn.codeyang.course.service.TransferRuleService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course/transferRule")
@RequiredArgsConstructor
public class TransferRuleController extends BaseController {
    private final TransferRuleService transferRuleService;

    @PreAuthorize("@ss.hasPermi('course:transferRule:list')")
    @PostMapping("/page")
    public AjaxResult page(@RequestBody TransferRulePageRequest request) {
        IPage<TransferRule> result = transferRuleService.selectPageList(request);
        return success(result);
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
}
