package cn.codeyang.course.service;

import cn.codeyang.course.domain.Grade;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GradeService extends IService<Grade> {
    List<Grade> listOrderByGrade();

    List<Tree<Long>> tree();
}
