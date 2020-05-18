create table if not exists `product-msg-qrcode`.product
(
    id                   int auto_increment comment '主键' primary key,
    product_id           varchar(32)                    not null comment '产品标识符',
    product_name         varchar(64)  default ''        not null comment '产品名称',
    product_desc         varchar(128) default '此产品暂无描述' not null comment '产品描述',
    pruduct_created_time datetime                       not null comment '产品创建时间',
    product_qrcode       blob                           null,
    constraint product_id
        unique (product_id)
);



create table system_param
(
    param_code varchar(32) primary key comment '系统参数码',
    param_desc varchar(32) not null comment '系统参数描述'
);