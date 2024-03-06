package cn.codeyang.course.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelUtilTest {

    @Test
    public void read() {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("/Users/yangzy/Desktop/课程表.xlsx"));
        List<Map<String, Object>> all = reader.readAll();
        System.out.println(all);
    }
}
