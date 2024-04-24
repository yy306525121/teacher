package cn.codeyang.course.service;

import cn.codeyang.course.domain.FillRule;
import cn.codeyang.course.dto.fillRule.FillRulePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface FillRuleService extends IService<FillRule> {
    IPage<FillRule> selectPageList(FillRulePageRequest request);

    /**
     * 根据开始日期和结束日期查询适用的放假规则
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    List<FillRule> getListByDate(LocalDate startDate, LocalDate endDate);
}
