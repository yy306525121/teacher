package cn.codeyang.course.service;

import cn.codeyang.course.domain.ExamRule;
import cn.codeyang.course.dto.examRule.ExamRulePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface ExamRuleService extends IService<ExamRule> {
    IPage<ExamRule> selectPageList(ExamRulePageRequest request);

    /**
     * 根据日期范围查询适用的考试规则
     * @param startDate
     * @param endDate
     * @return
     */
    List<ExamRule> getListByDate(LocalDate startDate, LocalDate endDate);
}
