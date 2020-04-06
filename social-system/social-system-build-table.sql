create table if not exists sys_user
(
    id        int unsigned primary key auto_increment,
    username  varchar(32)      not null unique
        comment '用户名',
    password  varchar(64)      not null
        comment '密码',
    phone     varchar(16)      not null default '' unique
        comment '手机号码',
    email     varchar(16)      not null default '' unique
        comment '邮箱',
    available tinyint unsigned not null default 1
        comment '账号是否可用'

) comment '用户信息表'
    character set utf8mb4
    collate utf8mb4_unicode_ci;

create table if not exists sys_user_chat_record
(
    id           int unsigned primary key auto_increment,
    from_user    int unsigned            not null
        comment '发起者',
    to_user      int unsigned            not null
        comment '接收者',
    message_type tinyint   default 1
        comment '文本消息/图片路径',
    message      text
        comment '消息主体/图片路径',
    send_status  tinyint   default 1
        comment '消息发送状态：未知/成功/网络原因失败/对方拒收',
    send_time    timestamp default now() not null
        comment '发送时间',
    receive_time timestamp
        comment '对方实际接收时间'
) comment '聊天记录信息表'
    character set utf8mb4
    collate utf8mb4_unicode_ci;

create table if not exists sys_user_relation
(
    id         int unsigned primary key auto_increment,
    from_user  int unsigned not null
        comment '发起者',
    to_user    int unsigned not null
        comment '接收者',
    behavior   tinyint      not null
        comment '好友请求/同意添加好友/拒绝添加好友/黑名单',
    result     tinyint
        comment '对方同意加好友/对方拒绝加好友',
    build_time timestamp comment '关系成立时间'
) comment '用户关系表'
    character set utf8mb4
    collate utf8mb4_unicode_ci;