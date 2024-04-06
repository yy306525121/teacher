package cn.codeyang.course.service;

import cn.codeyang.course.domain.TimeSlot;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TimeSlotService extends IService<TimeSlot> {
    TimeSlot getBySortInDay(int sortOfDay);
    TimeSlot getFirst();
    TimeSlot getLast();

    List<TimeSlot> listOrderBySortAsc();

    List<TimeSlot> getByType(Integer type);
}
