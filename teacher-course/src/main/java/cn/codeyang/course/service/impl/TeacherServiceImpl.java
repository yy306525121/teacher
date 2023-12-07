package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.domain.Teacher;
import cn.codeyang.course.domain.TeacherSubject;
import cn.codeyang.course.dto.teacher.TeacherAddRequest;
import cn.codeyang.course.dto.teacher.TeacherPageRequest;
import cn.codeyang.course.dto.teacher.TeacherPageResponse;
import cn.codeyang.course.dto.teacher.TeacherUpdateRequest;
import cn.codeyang.course.mapper.TeacherMapper;
import cn.codeyang.course.mapper.TeacherSubjectMapper;
import cn.codeyang.course.service.TeacherService;
import cn.codeyang.course.service.TeacherSubjectService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    private final TeacherSubjectService teacherSubjectService;

    @Override
    public List<Teacher> list(TeacherPageRequest request) {
        return baseMapper.selectList(Wrappers.<Teacher>lambdaQuery().like(StrUtil.isNotEmpty(request.getName()), Teacher::getName, request.getName()).eq(StrUtil.isNotEmpty(request.getStatus()), Teacher::getStatus, request.getStatus()).orderByDesc(Teacher::getCreateTime));
    }

    @Transactional
    @Override
    public int saveTeacher(TeacherAddRequest request) {
        Teacher teacher = BeanUtil.copyProperties(request, Teacher.class);
        int rows = baseMapper.insert(teacher);

        List<Long> subjectList = request.getSubjectList();
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

        List<Long> subjectList = request.getSubjectList();
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
    public IPage<TeacherPageResponse> selectPage(TeacherPageRequest request) {
        return baseMapper.selectPage(request.getPage(), request.getName(), request.getStatus());
    }
}
