package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.ExamRule;
import cn.codeyang.course.dto.examRule.ExamRulePageRequest;
import cn.codeyang.course.mapper.ExamRuleMapper;
import cn.codeyang.course.service.ExamRuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ExamRuleServiceImpl extends ServiceImpl<ExamRuleMapper, ExamRule> implements ExamRuleService {
    @Override
    public IPage<ExamRule> selectPageList(ExamRulePageRequest request) {
        LocalDate searchDate = request.getSearchDate();
        LocalDate startDate = searchDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = searchDate.with(TemporalAdjusters.lastDayOfMonth());
        return baseMapper.selectPage(request.getPage(), Wrappers.<ExamRule>lambdaQuery()
                .between(ExamRule::getStartDate, startDate, endDate)
                .or()
                .between(ExamRule::getEndDate, startDate, endDate));
    }

    @Override
    public List<ExamRule> getListByDate(LocalDate startDate, LocalDate endDate) {
        return baseMapper.selectList(Wrappers.<ExamRule>lambdaQuery()
                .between(ExamRule::getStartDate, startDate, endDate)
                .or()
                .between(ExamRule::getEndDate, startDate, endDate));
    }
}
