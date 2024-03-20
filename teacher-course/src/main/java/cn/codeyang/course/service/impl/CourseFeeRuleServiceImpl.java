package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CourseFeeRule;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageRequest;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageResponse;
import cn.codeyang.course.mapper.CourseFeeRuleMapper;
import cn.codeyang.course.service.CourseFeeRuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseFeeRuleServiceImpl extends ServiceImpl<CourseFeeRuleMapper, CourseFeeRule> implements CourseFeeRuleService {
    @Override
    public IPage<CourseFeeRule> selectPageList(CourseFeeRulePageRequest request) {
        LocalDate date = request.getDate();

        LocalDate startDate = null;
        LocalDate endDate = null;
        if (date != null) {
            startDate = date.withDayOfMonth(1);
            endDate = date.withDayOfMonth(date.lengthOfMonth());
        }

        LocalDate finalStartDate = startDate;
        LocalDate finalEndDate = endDate;
        return baseMapper.selectPage(request.getPage(), Wrappers.<CourseFeeRule>lambdaQuery()
                .lt(endDate != null, CourseFeeRule::getStartDate, endDate)
                .or()
                .ge(startDate != null, CourseFeeRule::getEndDate, startDate)
                .or(date != null, i -> i.between(date != null, CourseFeeRule::getOverrideDate, finalStartDate, finalEndDate))
                .orderByDesc(CourseFeeRule::getCreateTime)
        );
    }

    @Override
    public List<CourseFeeRulePageResponse> selectFeeRuleList(LocalDate date) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (date != null) {
            startDate = date.withDayOfMonth(1);
            endDate = date.withDayOfMonth(date.lengthOfMonth());
        }
        return baseMapper.selectFeeRuleList(startDate, endDate);
    }
}
