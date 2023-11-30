create table t_teacher
(
    id          bigint auto_increment comment '教师ID' primary key,
    name        varchar(32) comment '教师名称',
    status      char        default '0' comment '状态 0-在职、1-离职',
    del_flag    char        default '0' null comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64) default ''  null comment '创建者',
    create_time datetime                null comment '创建时间',
    update_by   varchar(64) default ''  null comment '更新者',
    update_time datetime                null comment '更新时间'
) engine = innodb comment = '教师表';


create table t_course_setting
(
    id                    bigint auto_increment comment '教师ID' primary key,
    day_of_per_week       int(10)     default 5 comment '每周上几天课',
    size_of_morning_early int(10)     default 0 comment '早自习几节课',
    size_of_morning       int(10)     default 4 comment '上午几节课',
    size_of_afternoon     int(10)     default 4 comment '下午几节课',
    size_of_night         int(10)     default 2 comment '晚自习几节课',
    del_flag              char        default '0' null comment '删除标志（0代表存在 2代表删除）',
    create_by             varchar(64) default ''  null comment '创建者',
    create_time           datetime                null comment '创建时间',
    update_by             varchar(64) default ''  null comment '更新者',
    update_time           datetime                null comment '更新时间'
) engine = innodb comment = '课程基础设置表';