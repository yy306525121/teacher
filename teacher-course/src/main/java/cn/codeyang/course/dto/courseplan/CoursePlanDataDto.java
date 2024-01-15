package cn.codeyang.course.dto.courseplan;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePlanDataDto implements Serializable {

    private static final long serialVersionUID = 2838958039150950845L;

    @ExcelProperty(value = "节数")
    private String numOfDay;
    @ExcelProperty(value = "星期一")
    private String monday;
    @ExcelProperty(value = "星期二")
    private String tuesday;
    @ExcelProperty(value = "星期三")
    private String wednesday;
    @ExcelProperty(value = "星期四")
    private String thursday;
    @ExcelProperty(value = "星期五")
    private String friday;
    @ExcelProperty(value = "星期六")
    private String saturday;
    @ExcelProperty(value = "星期日")
    private String sunday;
}
