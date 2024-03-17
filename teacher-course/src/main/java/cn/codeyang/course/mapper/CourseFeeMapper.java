package cn.codeyang.course.mapper;

import cn.codeyang.course.domain.CourseFee;
import cn.codeyang.course.dto.coursefee.CourseFeeDetailRspDto;
import cn.codeyang.course.dto.coursefee.CourseFeePageRspDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface CourseFeeMapper extends BaseMapper<CourseFee> {

    IPage<CourseFeePageRspDto> selectPageList(@Param("page") IPage<CourseFeePageRspDto> page, @Param("start") LocalDate start, @Param("end") LocalDate end);

    List<CourseFeeDetailRspDto> selectListGroupByDate(@Param("teacherId") Long teacherId, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
