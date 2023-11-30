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
) engine=innodb auto_increment=200 comment = '教师表';