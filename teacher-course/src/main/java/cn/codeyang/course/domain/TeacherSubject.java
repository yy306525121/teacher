package cn.codeyang.course.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_teacher_subject")
public class TeacherSubject extends Model<TeacherSubject> {
    private Long teacherId;
    private Long subjectId;
}
