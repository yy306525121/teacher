package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.mapper.TimeSlotMapper;
import cn.codeyang.course.service.TimeSlotService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotServiceImpl extends ServiceImpl<TimeSlotMapper, TimeSlot> implements TimeSlotService {
    @Override
    public TimeSlot getBySortInDay(int sortOfDay) {
        return this.baseMapper.selectOne(Wrappers.<TimeSlot>lambdaQuery().eq(TimeSlot::getSortInDay, sortOfDay));
    }

    @Override
    public TimeSlot getFirst() {
        return this.baseMapper.selectOne(Wrappers.<TimeSlot>lambdaQuery().orderByAsc(TimeSlot::getSortInDay).last("limit 1"));
    }

    @Override
    public TimeSlot getLast() {
        return this.baseMapper.selectOne(Wrappers.<TimeSlot>lambdaQuery().orderByDesc(TimeSlot::getSortInDay).last("limit 1"));
    }

    @Override
    public List<TimeSlot> listOrderBySortAsc() {
        return baseMapper.selectList(Wrappers.<TimeSlot>lambdaQuery().orderByAsc(TimeSlot::getSortInDay));
    }

    @Override
    public List<TimeSlot> getByType(Integer type) {
        return baseMapper.selectList(Wrappers.<TimeSlot>lambdaQuery().eq(TimeSlot::getType, type));
    }

    @Override
    public List<TimeSlot> selectListByType(Integer type) {
        return baseMapper.selectList(Wrappers.<TimeSlot>lambdaQuery().eq(TimeSlot::getType, type));
    }
}
