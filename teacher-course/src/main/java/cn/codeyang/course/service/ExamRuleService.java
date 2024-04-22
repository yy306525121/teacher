package cn.codeyang.course.service;

import cn.codeyang.course.domain.ExamRule;
import cn.codeyang.course.dto.examRule.ExamRulePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ExamRuleService extends IService<ExamRule> {
    IPage<ExamRule> selectPageList(ExamRulePageRequest request);
}
