package cn.codeyang.course.service;

import cn.codeyang.course.domain.TransferRule;
import cn.codeyang.course.dto.transferRule.TransferRulePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface TransferRuleService extends IService<TransferRule> {
    IPage<TransferRule> selectPageList(TransferRulePageRequest request);

    /**
     * 根据日期范围查询适用的规则
     * @param startDate
     * @param endDate
     * @return
     */
    List<TransferRule> getListByDate(LocalDate startDate, LocalDate endDate);
}
