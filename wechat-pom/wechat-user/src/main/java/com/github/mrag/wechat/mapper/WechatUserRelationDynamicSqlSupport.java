package com.github.mrag.wechat.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;

import com.github.mrag.wechat.type.EnumRelationType;
import com.github.mrag.wechat.type.EnumStatus;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class WechatUserRelationDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final WechatUserRelation wechatUserRelation = new WechatUserRelation();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> fromUserId = wechatUserRelation.fromUserId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> toUserId = wechatUserRelation.toUserId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<EnumRelationType> relationType = wechatUserRelation.relationType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> createdTime = wechatUserRelation.createdTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> establishedTime = wechatUserRelation.establishedTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<EnumStatus> status = wechatUserRelation.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class WechatUserRelation extends SqlTable {
        public final SqlColumn<Integer> fromUserId = column("from_user_id", JDBCType.INTEGER);

        public final SqlColumn<Integer> toUserId = column("to_user_id", JDBCType.INTEGER);

        public final SqlColumn<EnumRelationType> relationType = column("relation_type", JDBCType.TINYINT, "com.github.mrag.wechat.type.EnumRelationTypeHandler");

        public final SqlColumn<LocalDateTime> createdTime = column("created_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> establishedTime = column("established_time", JDBCType.TIMESTAMP);

        public final SqlColumn<EnumStatus> status = column("status", JDBCType.TINYINT, "com.github.mrag.wechat.type.EnumStatusHandler");

        public WechatUserRelation() {
            super("wechat_user_relation");
        }
    }
}