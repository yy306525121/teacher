package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.domain.CourseSetting;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanListRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanListRspDto;
import cn.codeyang.course.dto.courseplan.CoursePlanViewRspDto;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.CourseSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.codeyang.common.core.domain.AjaxResult.success;

@Slf4j
@RestController
@RequestMapping("/course/plan")
@RequiredArgsConstructor
public class CoursePlanController {
    private final CoursePlanService coursePlanService;
    private final CourseSettingService courseSettingService;

    @PreAuthorize("@ss.hasPermi('course:courseplan:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody CoursePlanListRequest request) {
        List<CoursePlanDto> list = coursePlanService.selectListByClassInfoId(request.getClassInfoId());
        CourseSetting courseSetting = courseSettingService.getCurrent();
        // 每天上几节课
        int numEveryDay = courseSetting.getSizeOfMorningEarly() + courseSetting.getSizeOfMorning() + courseSetting.getSizeOfAfternoon() + courseSetting.getSizeOfNight();

        int timeFirst = courseSetting.getSizeOfMorningEarly();
        int timeSecond = courseSetting.getSizeOfMorningEarly() + courseSetting.getSizeOfMorning();
        int timeThird = courseSetting.getSizeOfMorningEarly() + courseSetting.getSizeOfMorning() + courseSetting.getSizeOfAfternoon();

        List<CoursePlanViewRspDto> rspDtoList = new ArrayList<>();

        // 获取每天的第一节课
        for (int i = 1; i <= numEveryDay; i++) {
            int finalI = i;
            log.info("获取每天的第{}节课", finalI);

            int num = i;
            CoursePlanViewRspDto dto = new CoursePlanViewRspDto();
            if (i > 0 && i <= timeFirst) {
                dto.setTime("早自习");
            } else if (i > timeFirst && i <= timeSecond) {
                dto.setTime("上午");
                num = num - timeFirst;
            } else if (i > timeSecond && i <= timeThird) {
                num = num - timeSecond;
                dto.setTime("下午");
            } else if (i > timeThird) {
                num = num - timeThird;
                dto.setTime("夜自习");
            }

            List<CoursePlanDto> collect = list.stream().filter(item -> item.getTimeSlot().getSortOfDay() == finalI).collect(Collectors.toList());
            // 周一
            collect.stream().filter(item -> DayOfWeek.MONDAY.equals(item.getTimeSlot().getDayOfWeek())).findFirst().ifPresent(dto::setMonday);
            // 周二
            collect.stream().filter(item -> DayOfWeek.TUESDAY.equals(item.getTimeSlot().getDayOfWeek())).findFirst().ifPresent(dto::setTuesday);
            // 周三
            collect.stream().filter(item -> DayOfWeek.WEDNESDAY.equals(item.getTimeSlot().getDayOfWeek())).findFirst().ifPresent(dto::setWednesday);
            // 周四
            collect.stream().filter(item -> DayOfWeek.THURSDAY.equals(item.getTimeSlot().getDayOfWeek())).findFirst().ifPresent(dto::setThursday);
            // 周五
            collect.stream().filter(item -> DayOfWeek.FRIDAY.equals(item.getTimeSlot().getDayOfWeek())).findFirst().ifPresent(dto::setFriday);
            // 周六
            collect.stream().filter(item -> DayOfWeek.SATURDAY.equals(item.getTimeSlot().getDayOfWeek())).findFirst().ifPresent(dto::setSaturday);
            // 周末
            collect.stream().filter(item -> DayOfWeek.SUNDAY.equals(item.getTimeSlot().getDayOfWeek())).findFirst().ifPresent(dto::setSunday);
            dto.setNumInDay(num);
            rspDtoList.add(dto);
        }

        return success(rspDtoList);
    }
}
