package cn.codeyang.course.utils;

import cn.codeyang.course.domain.*;
import cn.codeyang.course.service.SubjectService;
import cn.codeyang.course.service.TeacherService;
import cn.codeyang.course.service.TimeSlotService;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class CoursePlanExcelUtil {
    public static final Map<String, Integer> WEEK_CONVERT_NUMBER = new HashMap<>(){{
        put("星期一", 1);
        put("星期二", 2);
        put("星期三", 3);
        put("星期四", 4);
        put("星期五", 5);
        put("星期六", 6);
        put("星期日", 7);
    }};

    /**
     * 获取sheet中的课程数据
     * @param sheet
     * @param classInfo 班级信息
     * @param weekRowIndex 星期所在行的索引（从0开始）
     * @param sortInDayCellIndex 节次所在列的索引（从0开始）
     * @return
     */
    public List<CoursePlan> getSheetData(Sheet sheet, ClassInfo classInfo, int weekRowIndex, int sortInDayCellIndex) {
        List<CoursePlan> coursePlanList = new ArrayList<>();
        SubjectService subjectService = SpringUtil.getBean(SubjectService.class);
        TeacherService teacherService = SpringUtil.getBean(TeacherService.class);

        // 迭代每一行，从第三行开始（索引为2）
        for (int rowIndex = weekRowIndex; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            // 如果行为空，则跳过
            if (row == null) continue;
            // 获取第二列的数据：第几节课
            Cell sortInDayCell = row.getCell(sortInDayCellIndex);
            String sortInDayStr = sortInDayCell != null ? sortInDayCell.getStringCellValue() : "";
            TimeSlot timeSlot = getTimeSlotFromStr(sortInDayStr);
            if (timeSlot == null) {
                throw new RuntimeException("班级：" + classInfo.getName() + "的课程时间段存在错误");
            }

            // 迭代每一列，从第二列开始
            for (int colIndex = sortInDayCellIndex + 1; colIndex < row.getLastCellNum(); colIndex++) {
                Cell cell = row.getCell(colIndex);
                if (cell != null) {
                    CoursePlan coursePlan = new CoursePlan();
                    coursePlan.setClassInfoId(classInfo.getId());
                    coursePlan.setTimeSlotId(timeSlot.getId());

                    Row headerRow = sheet.getRow(1);
                    Cell headerCell = headerRow.getCell(colIndex);
                    String weekStr = headerCell != null ? headerCell.getStringCellValue() : "";
                    Integer week = WEEK_CONVERT_NUMBER.get(weekStr);
                    if (week == null) {
                        throw new RuntimeException("班级：" + classInfo.getName() + " 的课程表中星期错误");
                    }
                    coursePlan.setDayOfWeek(week);

                    // 获取课程信息并输出
                    String courseStr = cell.getStringCellValue();
                    if (StrUtil.isEmpty(courseStr)) {
                        continue;
                    }
                    List<String> courseStrSplitList = StrUtil.splitTrim(courseStr, "\n");
                    if (courseStrSplitList.size() == 2) {
                        //正常的白天课时
                        String subjectName = courseStrSplitList.get(0);
                        String teacherName = courseStrSplitList.get(1);
                        Subject subject = subjectService.getByNameAndCreate(subjectName);
                        Teacher teacher = teacherService.getByNameAndCreate(teacherName);

                        coursePlan.setCourseTypeId(1);
                        coursePlan.setTeacherId(teacher.getId());
                        coursePlan.setSubjectId(subject.getId());
                    } else if (courseStrSplitList.size() == 1) {
                        if (timeSlot.getType() == 2) {
                            // 晚自习， 课程表中只有教师姓名
                            String teacherName = courseStrSplitList.get(0);
                            Teacher teacher = teacherService.getByNameAndCreate(teacherName);
                            coursePlan.setCourseTypeId(4);
                            coursePlan.setTeacherId(teacher.getId());
                        } else if (timeSlot.getType() == 3) {
                            // 白天自习课
                            String teacherName = courseStrSplitList.get(0);
                            Teacher teacher = teacherService.getByNameAndCreate(teacherName);
                            coursePlan.setCourseTypeId(2);
                            coursePlan.setTeacherId(teacher.getId());
                        }
                    }

                    coursePlanList.add(coursePlan);
                }
            }
        }
        return coursePlanList;
    }

    public TimeSlot getTimeSlotFromStr(String timeSlotStr) {
        if (StrUtil.isEmpty(timeSlotStr)) {
            return null;
        }

        String[] split = timeSlotStr.split("\\n");
        String sortStr = split[0];
        if (split.length > 1) {
            String timeScale = split[1];
        }

        String sort = ReUtil.getGroup0("\\d+", sortStr);
        if (StrUtil.isEmpty(sort)) {
            return null;
        }
        TimeSlotService timeSlotService = SpringUtil.getBean(TimeSlotService.class);
        TimeSlot timeSlot = timeSlotService.getBySortInDay(Integer.parseInt(sort) + 1);

        return timeSlot;
    }


}
