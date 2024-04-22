package cn.codeyang.course.dto.holidayRule;

import cn.codeyang.common.core.dto.RequestTemplate;
import cn.codeyang.course.domain.HolidayRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class HolidayRulePageRequest extends RequestTemplate<HolidayRule> {
    private LocalDate searchDate;
}
