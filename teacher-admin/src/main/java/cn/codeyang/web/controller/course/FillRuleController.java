package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.FillRule;
import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.dto.fillRule.FillRuleAddRequest;
import cn.codeyang.course.dto.fillRule.FillRuleEditRequest;
import cn.codeyang.course.dto.fillRule.FillRulePageRequest;
import cn.codeyang.course.dto.fillRule.FillRulePageRsp;
import cn.codeyang.course.service.ClassInfoService;
import cn.codeyang.course.service.FillRuleService;
import cn.codeyang.course.service.TimeSlotService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course/fillRule")
@RequiredArgsConstructor
public class FillRuleController extends BaseController {
    private final FillRuleService fillRuleService;
    private final TimeSlotService timeSlotService;
    private final ClassInfoService classInfoService;

    @PreAuthorize("@ss.hasPermi('course:fillRule:list')")
    @PostMapping("/page")
    public AjaxResult page(@RequestBody FillRulePageRequest request) {
        IPage<FillRule> result = fillRuleService.selectPageList(request);
        List<FillRule> sourceRecords = result.getRecords();
        List<FillRulePageRsp> targetRecords = new ArrayList<>();

        List<TimeSlot> timeSlotList = timeSlotService.list();
        List<ClassInfo> classInfoList = classInfoService.list();
        for (FillRule sourceRecord : sourceRecords) {
            FillRulePageRsp targetRecord = BeanUtil.copyProperties(sourceRecord, FillRulePageRsp.class);

            JSONArray jsonArray = JSONUtil.parseArray(sourceRecord.getClassInfoId());
            List<ClassInfo> classInfos = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                String classInfoId = jsonArray.getStr(i);
                ClassInfo classInfo = classInfoList.stream().filter(item -> item.getId().toString().equals(classInfoId)).findFirst().orElse(null);
                if (classInfo != null) {
                    classInfos.add(classInfo);
                }
            }
            targetRecord.setClassInfoList(classInfos);

            TimeSlot startTimeSlot = timeSlotList.stream().filter(item -> item.getId().equals(sourceRecord.getStartTimeSlotId())).findFirst().orElse(null);
            TimeSlot endTimeSlot = timeSlotList.stream().filter(item -> item.getId().equals(sourceRecord.getEndTimeSlotId())).findFirst().orElse(null);
            targetRecord.setStartTimeSlot(startTimeSlot);
            targetRecord.setEndTimeSlot(endTimeSlot);
            targetRecords.add(targetRecord);
        }


        IPage<FillRulePageRsp> pageResult = new Page<>();
        pageResult.setCurrent(result.getCurrent());
        pageResult.setPages(result.getPages());
        pageResult.setSize(result.getSize());
        pageResult.setTotal(result.getTotal());
        pageResult.setRecords(targetRecords);
        return success(pageResult);
    }

    @PreAuthorize("@ss.hasPermi('course:fillRule:list')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        return AjaxResult.success(fillRuleService.getById(id));
    }

    /**
     * 新增课时费计算规则
     */
    @PreAuthorize("@ss.hasPermi('course:fillRule:add')")
    @Log(title = "补课计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody FillRuleAddRequest request) {
        FillRule rule = BeanUtil.copyProperties(request, FillRule.class);
        return toAjax(fillRuleService.save(rule));
    }


    @PreAuthorize("@ss.hasPermi('course:fillRule:edit')")
    @Log(title = "补课计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    public AjaxResult edit(@Valid @RequestBody FillRuleEditRequest request) {
        FillRule rule = BeanUtil.copyProperties(request, FillRule.class);
        return toAjax(fillRuleService.updateById(rule));
    }

    @PreAuthorize("@ss.hasPermi('course:fillRule:remove')")
    @Log(title = "补课规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable(value = "ids") List<String> ids) {
        return toAjax(fillRuleService.removeByIds(ids));
    }
}
