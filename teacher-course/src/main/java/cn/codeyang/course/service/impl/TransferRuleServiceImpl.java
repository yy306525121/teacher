package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.TransferRule;
import cn.codeyang.course.dto.transferRule.TransferRulePageRequest;
import cn.codeyang.course.mapper.TransferRuleMapper;
import cn.codeyang.course.service.TransferRuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class TransferRuleServiceImpl extends ServiceImpl<TransferRuleMapper, TransferRule> implements TransferRuleService {
    @Override
    public IPage<TransferRule> selectPageList(TransferRulePageRequest request) {
        LocalDate searchDate = request.getSearchDate();
        LocalDate startDate = searchDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = searchDate.with(TemporalAdjusters.lastDayOfMonth());

        return baseMapper.selectPage(request.getPage(), Wrappers.<TransferRule>lambdaQuery()
                .between(TransferRule::getOverrideDate, startDate, endDate));
    }

    @Override
    public List<TransferRule> getListByDate(LocalDate startDate, LocalDate endDate) {
        return baseMapper.selectList(Wrappers.<TransferRule>lambdaQuery()
                .between(TransferRule::getOverrideDate, startDate, endDate));
    }
}
