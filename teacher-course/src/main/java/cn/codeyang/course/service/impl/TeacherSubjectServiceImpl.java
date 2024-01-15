package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.TeacherSubject;
import cn.codeyang.course.mapper.TeacherSubjectMapper;
import cn.codeyang.course.service.TeacherSubjectService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherSubjectServiceImpl extends ServiceImpl<TeacherSubjectMapper, TeacherSubject> implements TeacherSubjectService {
    @Override
    public int deleteByTeacherId(Long teacherId) {
        return baseMapper.delete(Wrappers.<TeacherSubject>lambdaQuery()
                .eq(TeacherSubject::getTeacherId, teacherId));
    }

    @Override
    public TeacherSubject selectOneByTeacherIdAndSubjectId(Long teacherId, Long subjectId) {
        return baseMapper.selectOne(Wrappers.<TeacherSubject>lambdaQuery()
                .eq(TeacherSubject::getTeacherId, teacherId)
                .eq(TeacherSubject::getSubjectId, subjectId));
    }
}
