package cn.codeyang.course.service;

import cn.codeyang.course.domain.HolidayRule;
import cn.codeyang.course.dto.holidayRule.HolidayRulePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface HolidayRuleService extends IService<HolidayRule> {
    IPage<HolidayRule> selectPageList(HolidayRulePageRequest request);
}
