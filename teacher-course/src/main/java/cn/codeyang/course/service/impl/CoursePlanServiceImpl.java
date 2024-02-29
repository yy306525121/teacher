package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanDto;
import cn.codeyang.course.dto.courseplan.CoursePlanFilterDto;
import cn.codeyang.course.mapper.CoursePlanMapper;
import cn.codeyang.course.service.CoursePlanService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class CoursePlanServiceImpl extends ServiceImpl<CoursePlanMapper, CoursePlan> implements CoursePlanService {
    @Override
    public List<CoursePlanDto> selectListByClassInfoId(Long classInfoId) {
        return baseMapper.selectListByClassInfoId(classInfoId);
    }

    @Override
    public List<CoursePlanDto> selectListByWeekAndTeacherId(Integer week, Long teacherId, List<CoursePlanFilterDto> filter) {
        return baseMapper.selectListByWeekAndTeacherId(week, teacherId, filter);
    }

    @Override
    public List<CoursePlan> selectListByClassInfoIdList(List<Long> classInfoIdList) {
        return this.baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .in(CoursePlan::getClassInfoId, classInfoIdList));
    }
}
