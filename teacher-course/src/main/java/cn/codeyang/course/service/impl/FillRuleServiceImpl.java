package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.FillRule;
import cn.codeyang.course.dto.fillRule.FillRulePageRequest;
import cn.codeyang.course.mapper.FillRuleMapper;
import cn.codeyang.course.service.FillRuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class FillRuleServiceImpl extends ServiceImpl<FillRuleMapper, FillRule> implements FillRuleService {
    /**
     * 分页查询填充规则列表
     *
     * @param request 填充规则分页请求对象
     * @return 填充规则分页结果对象
     */
    @Override
    public IPage<FillRule> selectPageList(FillRulePageRequest request) {
        LocalDate searchDate = request.getSearchDate();
        LocalDate startDate = searchDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = searchDate.with(TemporalAdjusters.lastDayOfMonth());
        return baseMapper.selectPage(request.getPage(), Wrappers.<FillRule>lambdaQuery()
                .between(FillRule::getDate, startDate, endDate));
    }

    /**
     * 根据日期范围获取填充规则列表
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 填充规则列表
     */
    @Override
    public List<FillRule> getListByDate(LocalDate startDate, LocalDate endDate) {
        return baseMapper.selectList(Wrappers.<FillRule>lambdaQuery()
                .between(FillRule::getDate, startDate, endDate));
    }
}
