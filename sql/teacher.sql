create table t_teacher
(
    id          bigint auto_increment comment '教师ID' primary key,
    name        varchar(32) comment '教师名称',
    status      char        default '0' comment '状态 0-在职、1-离职',
    basic_salary decimal(10, 2) default 0.00 comment '基础工资',
    course_salary decimal(10, 2) default 0.00 comment '课时费',
    del_flag    tinyint(1)  default 0  null comment '删除标志（0代表存在 1代表删除）',
    create_by   varchar(64) default '' null comment '创建者',
    create_time datetime               null comment '创建时间',
    update_by   varchar(64) default '' null comment '更新者',
    update_time datetime               null comment '更新时间'
) engine = innodb comment = '教师表';


create table t_course_setting
(
    id                    bigint auto_increment comment '教师ID' primary key,
    day_of_per_week       int(10)     default 5 comment '每周上几天课',
    size_of_morning_early int(10)     default 0 comment '早自习几节课',
    size_of_morning       int(10)     default 4 comment '上午几节课',
    size_of_afternoon     int(10)     default 4 comment '下午几节课',
    size_of_night         int(10)     default 2 comment '晚自习几节课',
    del_flag              tinyint(1)  default 0  null comment '删除标志（0代表存在 2代表删除）',
    create_by             varchar(64) default '' null comment '创建者',
    create_time           datetime               null comment '创建时间',
    update_by             varchar(64) default '' null comment '更新者',
    update_time           datetime               null comment '更新时间'
) engine = innodb comment = '课程基础设置表';


create table t_class_info
(
    id          bigint auto_increment comment 'ID'
        primary key,
    name        varchar(30)            not null comment '年级',
    parent_id   bigint                 not null default 0,
    sort        int(1)      default 0 comment '排序',
    del_flag    tinyint(1)  default 0  null comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64) default '' null comment '创建者',
    create_time datetime               null comment '创建时间',
    update_by   varchar(64) default '' null comment '更新者',
    update_time datetime               null comment '更新时间'
) engine = innodb comment '年级信息表';


create table t_subject
(
    id          bigint auto_increment comment 'ID'
        primary key,
    name        varchar(30)            not null comment '科目名称',
    sort        int(1)      default 0 comment '排序',
    del_flag    tinyint(1)  default 0  null comment '删除标志（0代表存在 1代表删除）',
    create_by   varchar(64) default '' null comment '创建者',
    create_time datetime               null comment '创建时间',
    update_by   varchar(64) default '' null comment '更新者',
    update_time datetime               null comment '更新时间'
) engine = innodb comment '科目信息表';


create table t_course_plan
(
    id            bigint auto_increment comment '主键ID'
        primary key,
    class_info_id bigint                 not null comment '班级ID',
    teacher_id    bigint comment '教师ID',
    subject_id    bigint comment '课程ID',
    time_slot_id    bigint comment '课程时间ID',
    day_of_week     tinyint(1) comment '星期',
    course_type_id  bigint comment '课程类型ID',
    del_flag      tinyint(1)  default 0  not null comment '删除标志（0代表存在 1代表删除）',
    create_by     varchar(64) default '' null comment '创建者',
    create_time   datetime               null comment '创建时间',
    update_by     varchar(64) default '' null comment '更新者',
    update_time   datetime               null comment '更新时间'
)
    comment '课程表';


-- auto-generated definition
create table t_course_fee
(
    id            bigint auto_increment comment 'ID'
        primary key,
    count         decimal(4, 1) default 0 comment '课时',
    teacher_id    bigint        default null comment '教师ID',
    class_info_id bigint        default null comment '教室ID',
    subject_id    bigint        default null comment '课程ID',
    week          int                       not null comment '星期几',
    num_in_day    int                       not null comment '第几节课',
    date          date                      not null comment '日期',
    del_flag      char          default '0' null comment '删除标志（0代表存在 2代表删除）',
    create_by     varchar(64)   default ''  null comment '创建者',
    create_time   datetime                  null comment '创建时间',
    update_by     varchar(64)   default ''  null comment '更新者',
    update_time   datetime                  null comment '更新时间'
)
    comment '课时费';

create table t_time_slot
(
    id          bigint auto_increment comment 'id'
        primary key,
    start_time  time           null comment '开始时间',
    end_time    time           null comment '结束时间',
    type        tinyint(1)     null comment '类型 1:课时 2:早自习 3:晚自习',
    del_flag    tinyint(1)  default 0  not null comment '删除标志（0代表存在 1代表删除）',
    create_by   varchar(64) default '' null comment '创建者',
    create_time datetime               null comment '创建时间',
    update_by   varchar(64) default '' null comment '更新者',
    update_time datetime               null comment '更新时间'
) comment '课表时段表';


create table t_course_type
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(32)              null comment '类型名称',
    type        int                      null comment 'type值 1:正常课程、2:自习课、3:早自习',
    course_period         decimal(5, 2) default 0  not null comment '每个课程的课时数',
    del_flag    tinyint(1)    default 0  not null comment '删除标志（0代表存在 1代表删除）',
    create_by   varchar(64)   default '' null comment '创建者',
    create_time datetime                 null comment '创建时间',
    update_by   varchar(64)   default '' null comment '更新者',
    update_time datetime                 null comment '更新时间'
)
    comment '课程类型表（正常课程、自习课、早自习）';




-- auto-generated definition
create table t_transfer_rule
(
    id                           bigint auto_increment comment 'ID'
        primary key,
    override_date                date                   null comment '调课时间',
    override_time_slot_id        bigint                 null comment '调课节次',
    override_from_teacher_id     bigint                 null comment '调课教师ID',
    override_to_teacher_id       bigint                 null comment '调课教师ID',
    override_from_subject_id     bigint                 null comment '调课课程',
    override_to_subject_id       bigint                 null comment '调课课程',
    override_from_course_type_id bigint                 null comment '调课课程类型',
    override_to_course_type_id   bigint                 null comment '调课课程类型',
    del_flag                     tinyint(1)  default 0  null comment '删除标志（0代表存在 1代表删除）',
    create_by                    varchar(64) default '' null comment '创建者',
    create_time                  datetime               null comment '创建时间',
    update_by                    varchar(64) default '' null comment '更新者',
    update_time                  datetime               null comment '更新时间'
)
    comment '调课规则表' collate = utf8mb4_unicode_ci;



create table t_exam_rule
(
    id                 bigint auto_increment comment 'ID'
        primary key,
    class_info_id      varchar(200)           null comment '班级ID的数组json',
    start_date         date                   null comment '开始日期',
    start_time_slot_id bigint                 null comment '开始节次',
    end_date           date                   null comment '结束日期',
    end_time_slot_id   bigint                 null comment '结束节次',
    del_flag           tinyint(1)  default 0  null comment '删除标志（0代表存在 1代表删除）',
    create_by          varchar(64) default '' null comment '创建者',
    create_time        datetime               null comment '创建时间',
    update_by          varchar(64) default '' null comment '更新者',
    update_time        datetime               null comment '更新时间'
)
    comment '考试时间规则' collate = utf8mb4_unicode_ci;


create table t_holiday_rule
(
    id                 bigint auto_increment comment 'ID'
        primary key,
    class_info_id      varchar(200)           null comment '班级ID的数组json',
    start_date         date                   null comment '开始日期',
    start_time_slot_id bigint                 null comment '开始节次',
    end_date           date                   null comment '结束日期',
    end_time_slot_id   bigint                 null comment '结束节次',
    del_flag           tinyint(1)  default 0  null comment '删除标志（0代表存在 1代表删除）',
    create_by          varchar(64) default '' null comment '创建者',
    create_time        datetime               null comment '创建时间',
    update_by          varchar(64) default '' null comment '更新者',
    update_time        datetime               null comment '更新时间'
)
    comment '放假时间规则' collate = utf8mb4_unicode_ci;

create table t_fill_rule
(
    id                 bigint auto_increment comment 'ID'
        primary key,
    date               date                   not null comment '补课日期',
    source_week        int                    not null comment '补周几的课',
    start_time_slot_id bigint                 null comment '开始补课节次',
    end_time_slot_id   bigint                 null comment '结束补课节次',
    del_flag           tinyint(1)  default 0  null comment '删除标志（0代表存在 1代表删除）',
    create_by          varchar(64) default '' null comment '创建者',
    create_time        datetime               null comment '创建时间',
    update_by          varchar(64) default '' null comment '更新者',
    update_time        datetime               null comment '更新时间'
)
    comment '补课规则表' collate = utf8mb4_unicode_ci;

