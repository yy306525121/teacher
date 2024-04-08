package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.CourseType;
import cn.codeyang.course.dto.classinfo.ClassInfoAddRequest;
import cn.codeyang.course.dto.classinfo.ClassInfoListRequest;
import cn.codeyang.course.dto.classinfo.ClassInfoUpdateRequest;
import cn.codeyang.course.service.ClassInfoService;
import cn.codeyang.course.service.CourseTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 年级班级
 */
@RestController
@RequestMapping("/course/type")
@RequiredArgsConstructor
public class CourseTypeController extends BaseController {
    private final CourseTypeService courseTypeService;

    @PreAuthorize("@ss.hasPermi('course:type:list')")
    @PostMapping("/list")
    public AjaxResult list() {
        List<CourseType> list = courseTypeService.list();
        return success(list);
    }
}
