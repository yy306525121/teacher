package cn.codeyang.course.service;

import cn.codeyang.common.core.domain.entity.SysUser;
import cn.codeyang.course.domain.CourseFeeRule;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageRequest;
import cn.codeyang.course.dto.feeRule.CourseFeeRulePageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface CourseFeeRuleService extends IService<CourseFeeRule> {
    IPage<CourseFeeRule> selectPageList(CourseFeeRulePageRequest request);

    /**
     * 查询放假和考试类型的规则
     * @param date
     * @return
     */
    List<CourseFeeRulePageResponse> selectFeeRuleList(LocalDate date);

    List<CourseFeeRule> selectChangeTypeList(LocalDate date);
}
