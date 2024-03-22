package cn.codeyang.course.service;

import cn.codeyang.course.domain.TimeSlot;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TimeSlotService extends IService<TimeSlot> {
    TimeSlot getBySortInDay(int sortOfDay);
    TimeSlot getFirst();
    TimeSlot getLast();
}
