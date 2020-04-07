CREATE TABLE user_msg
(
    id            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    username      VARCHAR(32)  NOT NULL COMMENT '用户名' unique,
    nickname      VARCHAR(32)  NOT NULL COMMENT '昵称',
    password      VARCHAR(32)  NOT NULL COMMENT '密码',
    phone         VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '手机号' unique,
    email         VARCHAR(128) NOT NULL DEFAULT '' COMMENT '邮箱' unique,
    real_name     VARCHAR(32) COMMENT '真实姓名',
    sex           TINYINT COMMENT '性别',
    profile_photo VARCHAR(256) COMMENT '头像 图片路径',
    personal_sign VARCHAR(256) COMMENT '个性签名',
    created_time  TIMESTAMP COMMENT '创建时间',
    updated_time  TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT = '用户信息' character set utf8mb4
                   collate utf8mb4_unicode_ci;;

CREATE TABLE user_relation
(
    id             BIGINT  NOT NULL AUTO_INCREMENT COMMENT '主键',
    self_user      BIGINT  NOT NULL COMMENT '自己',
    another_user   BIGINT  NOT NULL COMMENT '对方',
    relation_type  TINYINT NOT NULL COMMENT '关系类型 1-关注，2-拉黑',
    operating_time TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (id),
    key (self_user),
    key (another_user)
) COMMENT = '用户关系' character set utf8mb4
                   collate utf8mb4_unicode_ci;;

CREATE TABLE communicate
(
    id            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    from_user     BIGINT        NOT NULL COMMENT '发起者',
    to_user       BIGINT        NOT NULL COMMENT '接收者',
    msg_type      TINYINT       NOT NULL COMMENT '消息类型 1-添加好友，2-聊天消息',
    msg_body      VARCHAR(3072) NOT NULL DEFAULT '' COMMENT '消息主体 验证消息 或 聊天消息',
    response_type TINYINT COMMENT '响应状态 0-等待响应，1-添加好友成功，2-添加好友失败，3-消息发送成功，4-消息发送失败',
    send_time     TIMESTAMP COMMENT '消息发送时间',
    response_time TIMESTAMP COMMENT '消息响应时间',
    PRIMARY KEY (id),
    key (from_user),
    key (to_user)
) COMMENT = '沟通消息' character set utf8mb4
                   collate utf8mb4_unicode_ci;;

