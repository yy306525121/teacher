package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.Subject;
import cn.codeyang.course.dto.subject.SubjectPageRequest;
import cn.codeyang.course.mapper.SubjectMapper;
import cn.codeyang.course.service.SubjectService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Override
    public List<Subject> list(SubjectPageRequest request) {
        return baseMapper.selectList(Wrappers.<Subject>lambdaQuery()
                        .like(StrUtil.isNotEmpty(request.getName()), Subject::getName, request.getName())
                .orderByAsc(Subject::getSort));
    }
}
