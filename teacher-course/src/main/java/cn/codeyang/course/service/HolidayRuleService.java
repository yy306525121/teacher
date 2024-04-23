package cn.codeyang.course.service;

import cn.codeyang.course.domain.HolidayRule;
import cn.codeyang.course.dto.holidayRule.HolidayRulePageRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRuleService extends IService<HolidayRule> {
    IPage<HolidayRule> selectPageList(HolidayRulePageRequest request);

    /**
     * 根据开始日期和结束日期查询适用的放假规则
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    List<HolidayRule> getListByDate(LocalDate startDate, LocalDate endDate);
}
