package cn.codeyang.course.service;

import cn.codeyang.common.exception.file.FileUploadException;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.utils.CoursePlanExcelUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class CoursePlanServiceTest {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CoursePlanService coursePlanService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherSubjectService teacherSubjectService;
    @Autowired
    private TimeSlotService timeSlotService;

    public static final Long classInfoId = 1762432714630848514L;

    @SneakyThrows
    @Test
    public void testImport() {
        String filePath = "/Users/yangzy/Documents/课程表/各班课程表/【高一】所有班级课表.xlsx";
        InputStream inputStream = ResourceUtil.getStream(filePath);
        Workbook workbook = WorkbookFactory.create(inputStream);

        //获取sheet数量
        int numberOfSheets = workbook.getNumberOfSheets();
        //遍历sheet
        for (int i = 0; i < numberOfSheets; i++) {
            String sheetName = workbook.getSheetName(i);
            ClassInfo classInfo = classInfoService.getOneByName(sheetName);
            if (classInfo == null) {
                throw new FileUploadException("班级" + sheetName + "不存在");
            }

            Sheet sheet = workbook.getSheetAt(i);
            List<CoursePlan> coursePlanList = CoursePlanExcelUtil.getSheetData(sheet, classInfo, 2, 1);
        }
    }
}
