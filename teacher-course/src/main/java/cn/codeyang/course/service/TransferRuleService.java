package cn.codeyang.course.service;

import cn.codeyang.course.domain.TransferRule;
import cn.codeyang.course.dto.transferRule.TransferRulePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TransferRuleService extends IService<TransferRule> {
    IPage<TransferRule> selectPageList(TransferRulePageRequest request);
}
