package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.domain.TeacherSubject;
import cn.codeyang.course.dto.teacher.TeacherAddRequest;
import cn.codeyang.course.dto.teacher.TeacherInfoRspDto;
import cn.codeyang.course.dto.teacher.TeacherPageRequest;
import cn.codeyang.course.dto.teacher.TeacherUpdateRequest;
import cn.codeyang.course.mapper.TeacherMapper;
import cn.codeyang.course.service.ClassInfoService;
import cn.codeyang.course.service.CoursePlanService;
import cn.codeyang.course.service.TeacherService;
import cn.codeyang.course.service.TeacherSubjectService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    private final TeacherSubjectService teacherSubjectService;
    private final CoursePlanService coursePlanService;
    private final ClassInfoService classInfoService;

    @Override
    public List<Teacher> list(TeacherPageRequest request) {
        return baseMapper.selectList(Wrappers.<Teacher>lambdaQuery().like(StrUtil.isNotEmpty(request.getName()), Teacher::getName, request.getName()).eq(StrUtil.isNotEmpty(request.getStatus()), Teacher::getStatus, request.getStatus()).orderByDesc(Teacher::getCreateTime));
    }

    @Transactional
    @Override
    public int saveTeacher(TeacherAddRequest request) {
        Teacher teacher = BeanUtil.copyProperties(request, Teacher.class);
        int rows = baseMapper.insert(teacher);

        List<Long> subjectList = request.getSubjectIds();
        if (CollUtil.isNotEmpty(subjectList)) {
            List<TeacherSubject> list = new ArrayList<>(subjectList.size());
            for (Long subjectId : subjectList) {
                TeacherSubject teacherSubject = new TeacherSubject();
                teacherSubject.setTeacherId(teacher.getId());
                teacherSubject.setSubjectId(subjectId);
                list.add(teacherSubject);
            }
            teacherSubjectService.saveBatch(list);
        }
        return rows;
    }

    @Override
    public int updateTeacher(TeacherUpdateRequest request) {
        Teacher teacher = BeanUtil.copyProperties(request, Teacher.class);
        int rows = baseMapper.updateById(teacher);

        List<Long> subjectList = request.getSubjectIds();
        if (CollUtil.isNotEmpty(subjectList)) {
            // 删除旧关联关系
            teacherSubjectService.deleteByTeacherId(teacher.getId());
            // 新增新关联关系
            List<TeacherSubject> list = new ArrayList<>();
            for (Long subjectId : subjectList) {
                TeacherSubject teacherSubject = new TeacherSubject();
                teacherSubject.setTeacherId(teacher.getId());
                teacherSubject.setSubjectId(subjectId);
                list.add(teacherSubject);
            }
            teacherSubjectService.saveBatch(list);
        }
        return rows;
    }

    @Override
    public TeacherInfoRspDto getInfo(Long id) {
        return baseMapper.getInfo(id);
    }

    @Override
    public IPage<Teacher> selectPageList(TeacherPageRequest request) {
        return baseMapper.selectPage(request.getPage(), Wrappers.<Teacher>lambdaQuery().like(StrUtil.isNotEmpty(request.getName()), Teacher::getName, request.getName()).eq(StrUtil.isNotEmpty(request.getStatus()), Teacher::getStatus, request.getStatus()).orderByDesc(Teacher::getCreateTime));
    }

    @Override
    public Teacher getByName(String name) {
        return baseMapper.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getName, name));
    }

    @Override
    public List<Teacher> selectListBySubjectName(String subjectName, List<Long> level2ClassInfoIdList, LocalDate date) {
        List<Long> teacherIds = new ArrayList<>();

        if (CollUtil.isNotEmpty(level2ClassInfoIdList)) {
            // 查询指定班级下的所有教师
            List<CoursePlan> coursePlanList = coursePlanService.selectListByClassInfoIdList(level2ClassInfoIdList, date);
            teacherIds = coursePlanList.stream().map(CoursePlan::getTeacherId)
                    .filter(Objects::nonNull).distinct().collect(Collectors.toList());
        }
        return baseMapper.selectListBySubjectName(subjectName, teacherIds);
    }

    @Override
    public Teacher getByNameAndCreate(String teacherName) {
        Teacher teacher = getByName(teacherName);
        if (teacher == null) {
            teacher = new Teacher();
            teacher.setName(teacherName);
            save(teacher);
        }
        return teacher;
    }
}
