package cn.codeyang.course.dto.feeRule;

import cn.codeyang.common.core.dto.RequestTemplate;
import cn.codeyang.course.domain.CourseFeeRule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseFeeRulePageRequest extends RequestTemplate<CourseFeeRule> {
    private LocalDate date;
}
