package cn.codeyang.course.service;

import cn.codeyang.course.domain.TimeSlot;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;

@ActiveProfiles("dev")
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TimeSlotServiceTest {
    @Autowired
    private TimeSlotService timeSlotService;

    @Test
    public void saveTest() {
        TimeSlot entity = new TimeSlot();
        entity.setDayOfWeek(DayOfWeek.MONDAY);
        entity.setSortOfDay(1);
        timeSlotService.save(entity);
    }

    @Test
    public void getByIdTest() {
        TimeSlot timeSlot = timeSlotService.getById(1);
        System.out.println(timeSlot);
    }
}
