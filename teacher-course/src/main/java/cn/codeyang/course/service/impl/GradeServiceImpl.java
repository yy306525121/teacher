package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.Grade;
import cn.codeyang.course.mapper.GradeMapper;
import cn.codeyang.course.service.GradeService;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public List<Grade> listOrderByGrade() {
        return this.baseMapper.selectList(Wrappers.<Grade>lambdaQuery().orderByAsc(Grade::getGradeName));
    }

    @Override
    public List<Tree<Long>> tree() {
        List<TreeNode<Long>> collect = baseMapper.selectList(Wrappers.<Grade>lambdaQuery().orderByAsc(Grade::getGradeName)).stream().map(getNodeFunction()).collect(Collectors.toList());

        return TreeUtil.build(collect, 0L);
    }

    private Function<Grade, TreeNode<Long>> getNodeFunction() {
        return grade -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(grade.getId());
            node.setName(grade.getGradeName());
            node.setParentId(grade.getParentId());
            node.setWeight(grade.getGradeName());
            return node;
        };
    }
}
