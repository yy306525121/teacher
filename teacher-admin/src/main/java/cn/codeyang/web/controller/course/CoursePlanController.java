package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanListRequest;
import cn.codeyang.course.service.CoursePlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/course/plan")
@RequiredArgsConstructor
public class CoursePlanController {
    private final CoursePlanService coursePlanService;

    @PreAuthorize("@ss.hasPermi('course:courseplan:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody CoursePlanListRequest request) {
        List<CoursePlanDto> list = coursePlanService.selectListByClassInfoId(request.getClassInfoId());
        return null;
    }
}
