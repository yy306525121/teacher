package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.CoursePlan;
import cn.codeyang.course.dto.courseplan.CoursePlanListRequest;
import cn.codeyang.course.dto.courseplan.CoursePlanListRspDto;
import cn.codeyang.course.mapper.CoursePlanMapper;
import cn.codeyang.course.service.CoursePlanService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CoursePlanServiceImpl extends ServiceImpl<CoursePlanMapper, CoursePlan> implements CoursePlanService {
    @Override
    public List<CoursePlanListRspDto> selectListByClassInfoId(Long classInfoId) {
        return baseMapper.selectListByClassInfoId(classInfoId);
    }

    @Override
    public List<CoursePlan> selectListByWeekAndTeacherId(int week, Long teacherId) {
        return baseMapper.selectList(Wrappers.<CoursePlan>lambdaQuery()
                .eq(CoursePlan::getWeek, week)
                .eq(teacherId != null, CoursePlan::getTeacherId, teacherId));
    }
}
