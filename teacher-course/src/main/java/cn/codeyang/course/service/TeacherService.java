package cn.codeyang.course.service;

import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.dto.teacher.TeacherAddRequest;
import cn.codeyang.course.dto.teacher.TeacherInfoRspDto;
import cn.codeyang.course.dto.teacher.TeacherPageRequest;
import cn.codeyang.course.dto.teacher.TeacherUpdateRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface TeacherService extends IService<Teacher> {
    List<Teacher> list(TeacherPageRequest request);

    int saveTeacher(TeacherAddRequest request);

    int updateTeacher(TeacherUpdateRequest request);

    TeacherInfoRspDto getInfo(Long id);

    IPage<Teacher> selectPageList(TeacherPageRequest request);

    Teacher getByName(String name);

    /**
     * 查询指定班级、指定课程、指定日期内课程有效的教师
     * @param subjectName 科目
     * @param level2ClassInfoIdList 班级范围
     * @param date 课程计划在该日期当天有效的数据
     * @return
     */
    List<Teacher> selectListBySubjectName(String subjectName, List<Long> level2ClassInfoIdList, LocalDate date);

    Teacher getByNameAndCreate(String teacherName);

    List<Teacher> getListBySubjectId(String subjectId);

    /**
     * 查询只教某一阶段的某个科目的老师
     * 比如：A老师只教高一的英语（返回）
     *      B老师同时教高一和高二的英语（不返回）
     * @param topClassInfoId
     * @return
     */
    List<Teacher> getListByTopClassInfoIdAndSubjectIdOnly(Long topClassInfoId, Long subjectId);
}
