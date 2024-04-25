package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.dto.classinfo.ClassInfoListRequest;
import cn.codeyang.course.mapper.ClassInfoMapper;
import cn.codeyang.course.service.ClassInfoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements ClassInfoService {
    @Override
    public List<ClassInfo> listOrderByGrade() {
        return this.baseMapper.selectList(Wrappers.<ClassInfo>lambdaQuery().orderByAsc(ClassInfo::getName));
    }

    @Override
    public List<Tree<Long>> tree() {
        List<TreeNode<Long>> collect = baseMapper.selectList(
                Wrappers.<ClassInfo>lambdaQuery()
                        .orderByAsc(ClassInfo::getName)
        ).stream().map(getNodeFunction()).collect(Collectors.toList());

        return TreeUtil.build(collect, 0L);
    }

    @Override
    public List<ClassInfo> list(ClassInfoListRequest request) {
        return baseMapper.selectList(Wrappers.<ClassInfo>lambdaQuery()
                .eq(request.getParentId() != null, ClassInfo::getParentId, request.getParentId())
                .orderByAsc(ClassInfo::getSort));
    }

    @Override
    public List<ClassInfo> listByParentIdList(List<Long> classInfoIdList) {
        return this.baseMapper.selectList(Wrappers.<ClassInfo>lambdaQuery()
                .in(ClassInfo::getParentId, classInfoIdList));
    }

    @Override
    public List<ClassInfo> selectListByParentId(Long parentId) {
        return baseMapper.selectList(Wrappers.<ClassInfo>lambdaQuery()
                .eq(ClassInfo::getParentId, parentId));
    }

    @Override
    public ClassInfo getOneByName(String className) {
        return this.baseMapper.selectOne(Wrappers.<ClassInfo>lambdaQuery()
                .eq(ClassInfo::getName, className));
    }

    @Override
    public List<ClassInfo> listLevel2ByNotIn(List<Long> classInfoIdList) {
        return baseMapper.selectList(Wrappers.<ClassInfo>lambdaQuery()
                .ne(ClassInfo::getParentId, 0)
                .notIn(CollUtil.isNotEmpty(classInfoIdList), ClassInfo::getId, classInfoIdList));
    }

    @Override
    public List<ClassInfo> listAllLevel2() {
        return baseMapper.selectList(Wrappers.<ClassInfo>lambdaQuery()
                .ne(ClassInfo::getParentId, 0));
    }

    private Function<ClassInfo, TreeNode<Long>> getNodeFunction() {
        return classInfo -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(classInfo.getId());
            node.setName(classInfo.getName());
            node.setParentId(classInfo.getParentId());
            node.setWeight(classInfo.getSort());

            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("sort", classInfo.getSort());
            node.setExtra(extra);
            return node;
        };
    }
}
