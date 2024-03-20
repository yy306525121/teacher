package cn.codeyang.course.mapper;

import cn.codeyang.course.domain.CourseFeeRule;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface CourseFeeRuleMapper extends BaseMapper<CourseFeeRule> {
    List<CourseFeeRulePageResponse> selectFeeRuleList(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
