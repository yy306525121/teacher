package cn.codeyang.web.controller.course;

import cn.codeyang.common.annotation.Log;
import cn.codeyang.common.core.controller.BaseController;
import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.enums.BusinessType;
import cn.codeyang.course.calculate.CourseFeeCalculateHandleChainService;
import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.dto.coursefee.*;
import cn.codeyang.course.service.CourseFeeService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course/fee")
@RequiredArgsConstructor
public class CourseFeeController extends BaseController {
    private final CourseFeeService courseFeeService;
    private final CourseFeeCalculateHandleChainService courseFeeCalculateHandleChainService;

    @Value("${fee.export-write-path}")
    private String exportWritePath;


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
        courseFeeService.removeByDateBetween(start, end);
        List<CourseFee> courseFeeList = courseFeeCalculateHandleChainService.execute(start, end);
        logger.info("课时计算完成，开始入库...");
        courseFeeService.saveBatch(courseFeeList);
        logger.info("课时计算完成，入库完成...");
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


    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('course:fee:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseFeeExportReqDTO request) throws IOException {
        LocalDate date = request.getDate();
        LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());
        List<CourseFeeExportRspDTO> dataList = courseFeeService.selectExportList(start, end);


        ExcelWriter writer = ExcelUtil.getWriter();
        List<Map<String, Object>> dataMap = buildData(date, dataList);

        writer.write(dataMap, true);
        writer.flush(response.getOutputStream());
        writer.close();
    }

    private List<Map<String, Object>> buildData(LocalDate date, List<CourseFeeExportRspDTO> dataList) {
        Map<String, List<CourseFeeExportRspDTO>> collect = dataList.stream().collect(Collectors.groupingBy(this::fetchGroupKey));

        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        collect.forEach((key, list) -> {
            String[] keySplit = key.split(StrUtil.DASHED);

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("年级", keySplit[0]);
            row.put("教师", keySplit[1]);
            LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());
            while (!start.isAfter(end)) {
                LocalDate finalStart = start;
                CourseFeeExportRspDTO dataItem = dataList.stream().filter(item -> item.getClassName().equals(keySplit[0]) && item.getTeacherName().equals(keySplit[1]) && finalStart.equals(item.getDate())).findFirst().orElse(null);
                String dateStr = LocalDateTimeUtil.format(start, "MM-dd");
                if (dataItem != null) {
                    row.put(dateStr, dataItem.getCount());
                } else {
                    row.put(dateStr, "0");
                }
                start = start.plusDays(1);
            }
            rows.add(row);
        });

        return rows;
    }

    private String fetchGroupKey(CourseFeeExportRspDTO e) {
        return e.getClassName() + StrUtil.DASHED + e.getTeacherName();
    }

}
