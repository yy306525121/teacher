package cn.codeyang.course.dto.fillRule;

import cn.codeyang.common.core.dto.RequestTemplate;
import cn.codeyang.course.domain.FillRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class FillRulePageRequest extends RequestTemplate<FillRule> {
    private LocalDate searchDate;
}
