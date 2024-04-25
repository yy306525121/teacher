package cn.codeyang.course.service;

import cn.codeyang.course.domain.ClassInfo;
import cn.codeyang.course.dto.classinfo.ClassInfoListRequest;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ClassInfoService extends IService<ClassInfo> {
    List<ClassInfo> listOrderByGrade();

    List<Tree<Long>> tree();

    List<ClassInfo> list(ClassInfoListRequest request);

    List<ClassInfo> listByParentIdList(List<Long> classInfoIdList);

    List<ClassInfo> selectListByParentId(Long parentId);

    ClassInfo getOneByName(String className);

    List<ClassInfo> listLevel2ByNotIn(List<Long> classInfoIdList);

    List<ClassInfo> listAllLevel2();
}
