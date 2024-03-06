package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.service.TimeSlotService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course/timeSlot")
@RequiredArgsConstructor
public class TimeSlotController extends BaseController {
    private final TimeSlotService timeSlotService;

    @PreAuthorize("@ss.hasPermi('course:coursePlan:list')")
    @PostMapping("/list")
    public AjaxResult page() {
        List<TimeSlot> list = timeSlotService.list(Wrappers.<TimeSlot>lambdaQuery().orderByAsc(TimeSlot::getSortInDay));
        return success(list);
    }
}
