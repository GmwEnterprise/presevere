create table wechat.wechat_user
(
	user_id int auto_increment comment '用户ID'
		primary key,
	username varchar(64) not null comment '用户名',
	password varchar(64) not null comment '密码',
	email varchar(128) null comment '绑定邮箱',
	created_time timestamp not null comment '创建时间',
	available tinyint not null comment '是否有效账户',
	constraint username
		unique (username)
)
comment '用户信息表';

create table wechat.wechat_user_relation
(
	from_user_id int not null comment '发起者',
	to_user_id int not null comment '关联者',
	relation_type tinyint not null comment '关系类型',
	created_time timestamp not null comment '发起时间',
	established_time timestamp not null comment '成立时间',
	status tinyint not null comment '有效/失效',
	primary key (from_user_id, to_user_id)
);

