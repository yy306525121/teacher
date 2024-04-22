package cn.codeyang.course.dto.transferRule;

import cn.codeyang.common.core.dto.RequestTemplate;
import cn.codeyang.course.domain.TransferRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransferRulePageRequest extends RequestTemplate<TransferRule> {
    private LocalDate searchDate;
}
