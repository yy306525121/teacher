package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.HolidayRule;
import cn.codeyang.course.dto.holidayRule.HolidayRulePageRequest;
import cn.codeyang.course.mapper.HolidayRuleMapper;
import cn.codeyang.course.service.HolidayRuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class HolidayRuleServiceImpl extends ServiceImpl<HolidayRuleMapper, HolidayRule> implements HolidayRuleService {
    @Override
    public IPage<HolidayRule> selectPageList(HolidayRulePageRequest request) {
        LocalDate searchDate = request.getSearchDate();
        LocalDate startDate = searchDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = searchDate.with(TemporalAdjusters.lastDayOfMonth());

        return baseMapper.selectPage(request.getPage(), Wrappers.<HolidayRule>lambdaQuery()
                .between(HolidayRule::getStartDate, startDate, endDate)
                .or()
                .between(HolidayRule::getEndDate, startDate, endDate));
    }
}
