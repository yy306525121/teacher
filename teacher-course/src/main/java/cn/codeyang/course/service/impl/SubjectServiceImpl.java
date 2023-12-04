package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.mapper.SubjectMapper;
import cn.codeyang.course.service.SubjectService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Override
    public List<Subject> listBySort() {
        return baseMapper.selectList(Wrappers.<Subject>lambdaQuery()
                .orderByAsc(Subject::getSort));
    }
}
