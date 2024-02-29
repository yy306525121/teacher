package cn.codeyang.course.service.impl;

import cn.codeyang.course.domain.TimeSlot;
import cn.codeyang.course.mapper.TimeSlotMapper;
import cn.codeyang.course.service.TimeSlotService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TimeSlotServiceImpl extends ServiceImpl<TimeSlotMapper, TimeSlot> implements TimeSlotService {
    @Override
    public TimeSlot getBySortOfDay(int sortOfDay) {
        return this.baseMapper.selectOne(Wrappers.<TimeSlot>lambdaQuery()
                .eq(TimeSlot::getSortOfDay, sortOfDay));
    }
}
