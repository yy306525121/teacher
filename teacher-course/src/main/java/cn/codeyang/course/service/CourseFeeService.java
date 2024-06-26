package cn.codeyang.course.service;

import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.dto.coursefee.CourseFeeDetailRspDto;
import cn.codeyang.course.dto.coursefee.CourseFeeExportRspDTO;
import cn.codeyang.course.dto.coursefee.CourseFeePageRspDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface CourseFeeService extends IService<CourseFee> {

    IPage<CourseFeePageRspDto> selectPageList(Page<CourseFeePageRspDto> page, LocalDate start, LocalDate end);

    /**
     * 课时费计算
     * @param start
     * @param end
     */
    void calculate(Long teacherId, LocalDate start, LocalDate end);

    List<CourseFeeDetailRspDto> selectListGroupByDate(Long teacherId, LocalDate start, LocalDate end);

    List<CourseFeeExportRspDTO> selectExportList(LocalDate start, LocalDate end);

    void removeByDateBetween(LocalDate start, LocalDate end);
}
