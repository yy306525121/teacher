package cn.codeyang.course.dto.examRule;

import cn.codeyang.common.core.dto.RequestTemplate;
import cn.codeyang.course.domain.ExamRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamRulePageRequest extends RequestTemplate<ExamRule> {
    private LocalDate searchDate;
}
