package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.course.dto.lessonfee.*;
import cn.codeyang.course.service.CourseFeeService;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course/fee")
@RequiredArgsConstructor
public class CourseFeeController extends BaseController {
    private final CourseFeeService courseFeeService;


    @PreAuthorize("@ss.hasPermi('course:fee:list')")
    @PostMapping("/page")
    public AjaxResult page(@RequestBody CourseFeePageReqDto request) {
        LocalDate date = request.getDate();
        LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());

        IPage<CourseFeePageRspDto> result = courseFeeService.selectPageList(request.getPage(), start, end);
        return success(result);
    }

    /**
     * 计算课时费
     * @param request
     * @return
     */
    @PreAuthorize("@ss.hasPermi('course:fee:calculate')")
    @PostMapping("/calculate")
    public AjaxResult calculate(@RequestBody CourseFeeCalculateReqDto request) {
        LocalDate date = request.getDate();
        LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());

        courseFeeService.calculate(request.getTeacherId(), start, end);
        return success();
    }

    /**
     * 课时费明细
     * @param request
     * @return
     */
    @PreAuthorize("@ss.hasPermi('course:fee:detail')")
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody CourseFeeDetailReqDto request) {
        Map<String, Object> rsp = new HashMap<>();

        LocalDate date = request.getDate();
        LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());

        List<CourseFeeDetailRspDto> rspList = courseFeeService.selectListGroupByDate(request.getTeacherId(), start, end);
        Map<LocalDate, BigDecimal> data = MapUtil.newHashMap();
        for (CourseFeeDetailRspDto dto : rspList) {
            data.put(dto.getDate(), dto.getCount());
        }

        rsp.put("data", data);
        rsp.put("start", start);
        rsp.put("end", end);
        return success(rsp);
    }
}
