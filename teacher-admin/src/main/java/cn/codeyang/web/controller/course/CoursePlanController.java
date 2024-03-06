package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.exception.file.FileUploadException;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanListRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanViewRspDto;
import cn.codeyang.course.service.ClassInfoService;
import cn.codeyang.course.service.CoursePlanService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
    private ClassInfoService classInfoService;

    @PreAuthorize("@ss.hasPermi('course:coursePlan:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody CoursePlanListRequest request) {
        return null;
    }

    @SneakyThrows
    public AjaxResult importCoursePlan(@RequestParam("file")MultipartFile file) {
        InputStream inputStream = file.getInputStream();

        Workbook workbook = WorkbookFactory.create(inputStream);

        //获取sheet数量
        int numberOfSheets = workbook.getNumberOfSheets();
        //遍历sheet
        for (int i = 0; i < numberOfSheets; i++) {
            String sheetName = workbook.getSheetName(i);
            ClassInfo classInfo = classInfoService.getOneByName(sheetName);
            if (classInfo == null) {
                throw new FileUploadException("班级不存在");
            }

            Sheet sheet = workbook.getSheetAt(i);
            getSheetData(sheet, classInfo, 2, 1);
        }

        return null;
    }

    private List<CoursePlan> getSheetData(Sheet sheet, ClassInfo classInfo, int weekRowIndex, int sortInDayCellIndex) {
        List<CoursePlan> coursePlanList = new ArrayList<>();

        // 迭代每一行，从第三行开始（索引为2）
        for (int rowIndex = weekRowIndex; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            // 如果行为空，则跳过
            if (row == null) continue;
            // 获取第二列的数据：第几节课
            Cell sortInDayCell = row.getCell(sortInDayCellIndex);
            String sortInDay = sortInDayCell != null ? sortInDayCell.getStringCellValue() : "";
            // 迭代每一列，从第二列开始
            for (int colIndex = sortInDayCellIndex + 1; colIndex < row.getLastCellNum(); colIndex++) {
                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    Row headerRow = sheet.getRow(1);
                    Cell headerCell = headerRow.getCell(colIndex);
                    String week = headerCell != null ? headerCell.getStringCellValue() : "";

                    // 获取课程信息并输出
                    String course = cell.getStringCellValue();
                }
            }
        }

        return coursePlanList;
    }
}
