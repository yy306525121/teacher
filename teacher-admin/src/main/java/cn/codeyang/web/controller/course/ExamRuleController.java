package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.ExamRule;
import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.dto.examRule.ExamRuleAddRequest;
import cn.codeyang.course.dto.examRule.ExamRuleEditRequest;
import cn.codeyang.course.dto.examRule.ExamRulePageRequest;
import cn.codeyang.course.dto.examRule.ExamRulePageRsp;
import cn.codeyang.course.service.ClassInfoService;
import cn.codeyang.course.service.ExamRuleService;
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
@RequestMapping("/course/examRule")
@RequiredArgsConstructor
public class ExamRuleController extends BaseController {
    private final ExamRuleService examRuleService;
    private final TimeSlotService timeSlotService;
    private final ClassInfoService classInfoService;

    @PreAuthorize("@ss.hasPermi('course:examRule:list')")
    @PostMapping("/page")
    public AjaxResult page(@RequestBody ExamRulePageRequest request) {
        IPage<ExamRule> result = examRuleService.selectPageList(request);
        List<ExamRule> sourceRecords = result.getRecords();
        List<ExamRulePageRsp> targetRecords = new ArrayList<>();

        List<ClassInfo> classInfoList = classInfoService.list();
        List<TimeSlot> timeSlotList = timeSlotService.list();
        for (ExamRule sourceRecord : sourceRecords) {
            ExamRulePageRsp targetRecord = BeanUtil.copyProperties(sourceRecord, ExamRulePageRsp.class);

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


        IPage<ExamRulePageRsp> pageResult = new Page<>();
        pageResult.setCurrent(result.getCurrent());
        pageResult.setPages(result.getPages());
        pageResult.setSize(result.getSize());
        pageResult.setTotal(result.getTotal());
        pageResult.setRecords(targetRecords);
        return success(pageResult);
    }

    @PreAuthorize("@ss.hasPermi('course:examRule:list')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable(value = "id") Long id) {
        return AjaxResult.success(examRuleService.getById(id));
    }

    /**
     * 新增课时费计算规则
     */
    @PreAuthorize("@ss.hasPermi('course:examRule:add')")
    @Log(title = "课时费计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Valid @RequestBody ExamRuleAddRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            return error("开始日期不能大于结束日期");
        }

        if (request.getStartDate().isEqual(request.getEndDate())) {
            TimeSlot startTimeSlot = timeSlotService.getById(request.getStartTimeSlotId());
            TimeSlot endTimeSlot = timeSlotService.getById(request.getEndTimeSlotId());
            if (startTimeSlot.getSortInDay() >= endTimeSlot.getSortInDay()) {
                return error("开始节次必须早于结束节次");
            }
        }
        ExamRule examRule = BeanUtil.copyProperties(request, ExamRule.class);
        return toAjax(examRuleService.save(examRule));
    }


    @PreAuthorize("@ss.hasPermi('course:examRule:edit')")
    @Log(title = "课时费计算规则", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    public AjaxResult edit(@Valid @RequestBody ExamRuleEditRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            return error("开始日期不能大于结束日期");
        }

        if (request.getStartDate().isEqual(request.getEndDate())) {
            TimeSlot startTimeSlot = timeSlotService.getById(request.getStartTimeSlotId());
            TimeSlot endTimeSlot = timeSlotService.getById(request.getEndTimeSlotId());
            if (startTimeSlot.getSortInDay() >= endTimeSlot.getSortInDay()) {
                return error("开始节次必须早于结束节次");
            }
        }
        ExamRule examRule = BeanUtil.copyProperties(request, ExamRule.class);
        return toAjax(examRuleService.updateById(examRule));
    }

    @PreAuthorize("@ss.hasPermi('course:examRule:remove')")
    @Log(title = "放假规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable(value = "ids") List<String> ids) {
        return toAjax(examRuleService.removeByIds(ids));
    }
}
