package cn.codeyang.course.service;

import cn.codeyang.course.domain.*;
import cn.codeyang.course.dto.courseplan.CoursePlanDataDto;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testImport() {
        String fileName = "/Users/yangzy/Documents/课程表/各班课程表/高三/高三11班课程表.xlsx";
        EasyExcel.read(fileName, CoursePlanDataDto.class, new ReadListener<CoursePlanDataDto>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<CoursePlanDataDto> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(CoursePlanDataDto data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            @SneakyThrows
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                log.info("存储数据库成功！");
                for (CoursePlanDataDto coursePlanDataDto : cachedDataList) {
                    //周一
                    processDay(coursePlanDataDto.getMonday(), 1, Integer.parseInt(coursePlanDataDto.getNumOfDay()));
                    processDay(coursePlanDataDto.getTuesday(), 2, Integer.parseInt(coursePlanDataDto.getNumOfDay()));
                    processDay(coursePlanDataDto.getWednesday(), 3, Integer.parseInt(coursePlanDataDto.getNumOfDay()));
                    processDay(coursePlanDataDto.getThursday(), 4, Integer.parseInt(coursePlanDataDto.getNumOfDay()));
                    processDay(coursePlanDataDto.getFriday(), 5, Integer.parseInt(coursePlanDataDto.getNumOfDay()));
                    processDay(coursePlanDataDto.getSaturday(), 6, Integer.parseInt(coursePlanDataDto.getNumOfDay()));
                    processDay(coursePlanDataDto.getSunday(), 7, Integer.parseInt(coursePlanDataDto.getNumOfDay()));
                }
            }
        }).sheet().doRead();
    }

    private void processDay(String dayContent, int week, int numOfDay) throws Exception {
        if (StrUtil.isEmpty(dayContent)) {
            return;
        }
        CoursePlan coursePlan = new CoursePlan();
        coursePlan.setClassInfoId(classInfoId);
        coursePlan.setDayOfWeek(week);
        // coursePlan.setNumInDay(numOfDay);

        dayContent = dayContent.replaceAll("\r\n", "\n");
        String[] mondayContentArr = dayContent.split("\n");
        String subjectName = "";
        String teacherName = "";

        if (mondayContentArr.length == 2) {
            subjectName = mondayContentArr[0];
            teacherName = mondayContentArr[1];
            // 正常上课
            coursePlan.setCourseTypeId(1);
        } else if (mondayContentArr.length == 1) {
            if (numOfDay == 1) {
                subjectName = mondayContentArr[0];
                // 早自习
                coursePlan.setCourseTypeId(3);
            } else if (numOfDay == 11 || numOfDay == 12) {
                teacherName = mondayContentArr[0];
                // 晚自习
                coursePlan.setCourseTypeId(4);

            } else {
                teacherName = mondayContentArr[0];
                // 自习课
                coursePlan.setCourseTypeId(2);
            }

        }

        if (StrUtil.isNotEmpty(subjectName)) {
            Subject subject = subjectService.getByName(subjectName);
            if (subject == null) {
                subject = new Subject();
                subject.setName(subjectName);
                subjectService.save(subject);
            }
            coursePlan.setSubjectId(subject.getId());
        }

        if (StrUtil.isNotEmpty(teacherName)) {
            Teacher teacher = teacherService.getByName(teacherName);
            if (teacher == null) {
                teacher = new Teacher();
                teacher.setName(teacherName);
                teacher.setStatus("0");
                teacherService.save(teacher);

                if (StrUtil.isNotEmpty(subjectName)) {
                    Subject subject = subjectService.getByName(subjectName);
                    TeacherSubject teacherSubject = teacherSubjectService.selectOneByTeacherIdAndSubjectId(teacher.getId(), subject.getId());
                    if (teacherSubject == null) {
                        teacherSubject = new TeacherSubject();
                        teacherSubject.setSubjectId(subject.getId());
                        teacherSubject.setTeacherId(teacher.getId());
                        teacherSubjectService.save(teacherSubject);
                    }
                }

            }
            coursePlan.setTeacherId(teacher.getId());
        }

        // timeslot
        TimeSlot timeSlot = timeSlotService.getBySortInDay(numOfDay);
        if (timeSlot == null) {
            throw new Exception("找不到对应的时间段");
        }
        coursePlan.setTimeSlotId(timeSlot.getId());

        coursePlanService.save(coursePlan);
    }
}
