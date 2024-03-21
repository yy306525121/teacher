package cn.codeyang.web.controller.course;

import cn.codeyang.common.core.domain.AjaxResult;
import cn.codeyang.common.core.domain.entity.SysUser;
import cn.codeyang.common.exception.file.FileUploadException;
import cn.codeyang.common.utils.poi.ExcelUtil;
import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.domain.TeacherSubject;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanListRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanViewRspDto;
import cn.codeyang.course.opta.domain.CoursePlanSolution;
import cn.codeyang.course.opta.domain.CoursePlanWeek;
import cn.codeyang.course.service.*;
import cn.codeyang.course.utils.CoursePlanExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverManager;
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
    private final ClassInfoService classInfoService;
    private final SubjectService subjectService;
    private final TeacherSubjectService teacherSubjectService;

    private final SolverManager<CoursePlanSolution, Long> solverManager;
    private final SolutionManager<CoursePlanSolution, HardSoftScore> solutionManager;

    private static final String SUBJECT_ENGLISH = "英语";
    private static final String SUBJECT_CHINESE = "语文";


    @PreAuthorize("@ss.hasPermi('course:coursePlan:solve')")
    @PostMapping("/solve/{problemId}")
    public void solve(@PathVariable("problemId") Long problemId) {
        solverManager.solveAndListen(problemId, coursePlanService::selectProbjemById, coursePlanService::saveSolution);
    }

    @PreAuthorize("@ss.hasPermi('course:coursePlan:list')")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody CoursePlanListRequest request) {
        Integer queryType = request.getQueryType();
        if (queryType == 1) {
            // 按班级查询
            if (request.getClassInfoId() == null) {
                return AjaxResult.error("请选择班级");
            }
            return success(coursePlanService.selectByClassInfoIdOrTeacherId(request.getClassInfoId(), null));
        } else if (queryType == 2) {
            // 按老师查询
            if (request.getTeacherId() == null) {
                return AjaxResult.error("请选择老师");
            }
            return success(coursePlanService.selectByClassInfoIdOrTeacherId(null, request.getTeacherId()));
        } else {
            return AjaxResult.error("查询类型错误");
        }
    }

    @SneakyThrows
    @PreAuthorize("@ss.hasPermi('course:coursePlan:import')")
    @PostMapping("/import")
    public AjaxResult importCoursePlan(@RequestParam("file")MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);

        Subject subjectEnglish = subjectService.getByNameAndCreate(SUBJECT_ENGLISH);
        Subject subjectChinese = subjectService.getByNameAndCreate(SUBJECT_CHINESE);

        List<TeacherSubject> teacherSubjectList = teacherSubjectService.list();

        List<CoursePlan> importList = new ArrayList<>();

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

            for (CoursePlan coursePlan : coursePlanList) {
                // 检查教师和课程的关联关系
                if (coursePlan.getTeacherId() != null && coursePlan.getSubjectId() != null) {
                    if (teacherSubjectList.stream().noneMatch(item -> item.getTeacherId().equals(coursePlan.getTeacherId()) && item.getSubjectId().equals(coursePlan.getSubjectId()))) {

                    }
                }
            }


            // 为当前的班级添加周一到周六的早自习课程
            for (int j = 1; j < 7; j++) {
                CoursePlan coursePlan = new CoursePlan();
                coursePlan.setClassInfoId(classInfo.getId());
                if (j % 2 == 1) {
                    coursePlan.setSubjectId(subjectChinese.getId());
                } else {
                    coursePlan.setSubjectId(subjectEnglish.getId());
                }
                coursePlan.setTimeSlotId(1L);
                coursePlan.setCourseTypeId(3);
                coursePlan.setDayOfWeek(j);
                coursePlanList.add(coursePlan);
            }
            importList.addAll(coursePlanList);
        }
        coursePlanService.saveBatch(importList);

        return AjaxResult.success("导入成功");
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        // ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        // util.importTemplateExcel(response, "用户数据");
    }


}
