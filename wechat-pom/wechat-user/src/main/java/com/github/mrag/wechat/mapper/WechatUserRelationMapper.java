package com.github.mrag.wechat.mapper;

import static com.github.mrag.wechat.mapper.WechatUserRelationDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.github.mrag.wechat.domain.WechatUserRelation;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface WechatUserRelationMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(fromUserId, toUserId, relationType, createdTime, establishedTime, status);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<WechatUserRelation> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<WechatUserRelation> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("WechatUserRelationResult")
    Optional<WechatUserRelation> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="WechatUserRelationResult", value = {
        @Result(column="from_user_id", property="fromUserId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="to_user_id", property="toUserId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="relation_type", property="relationType", jdbcType=JdbcType.TINYINT),
        @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="established_time", property="establishedTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT)
    })
    List<WechatUserRelation> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, wechatUserRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, wechatUserRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer fromUserId_, Integer toUserId_) {
        return delete(c -> 
            c.where(fromUserId, isEqualTo(fromUserId_))
            .and(toUserId, isEqualTo(toUserId_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(WechatUserRelation record) {
        return MyBatis3Utils.insert(this::insert, record, wechatUserRelation, c ->
            c.map(fromUserId).toProperty("fromUserId")
            .map(toUserId).toProperty("toUserId")
            .map(relationType).toProperty("relationType")
            .map(createdTime).toProperty("createdTime")
            .map(establishedTime).toProperty("establishedTime")
            .map(status).toProperty("status")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<WechatUserRelation> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, wechatUserRelation, c ->
            c.map(fromUserId).toProperty("fromUserId")
            .map(toUserId).toProperty("toUserId")
            .map(relationType).toProperty("relationType")
            .map(createdTime).toProperty("createdTime")
            .map(establishedTime).toProperty("establishedTime")
            .map(status).toProperty("status")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(WechatUserRelation record) {
        return MyBatis3Utils.insert(this::insert, record, wechatUserRelation, c ->
            c.map(fromUserId).toPropertyWhenPresent("fromUserId", record::getFromUserId)
            .map(toUserId).toPropertyWhenPresent("toUserId", record::getToUserId)
            .map(relationType).toPropertyWhenPresent("relationType", record::getRelationType)
            .map(createdTime).toPropertyWhenPresent("createdTime", record::getCreatedTime)
            .map(establishedTime).toPropertyWhenPresent("establishedTime", record::getEstablishedTime)
            .map(status).toPropertyWhenPresent("status", record::getStatus)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<WechatUserRelation> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, wechatUserRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<WechatUserRelation> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, wechatUserRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<WechatUserRelation> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, wechatUserRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<WechatUserRelation> selectByPrimaryKey(Integer fromUserId_, Integer toUserId_) {
        return selectOne(c ->
            c.where(fromUserId, isEqualTo(fromUserId_))
            .and(toUserId, isEqualTo(toUserId_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, wechatUserRelation, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(WechatUserRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(fromUserId).equalTo(record::getFromUserId)
                .set(toUserId).equalTo(record::getToUserId)
                .set(relationType).equalTo(record::getRelationType)
                .set(createdTime).equalTo(record::getCreatedTime)
                .set(establishedTime).equalTo(record::getEstablishedTime)
                .set(status).equalTo(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(WechatUserRelation record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(fromUserId).equalToWhenPresent(record::getFromUserId)
                .set(toUserId).equalToWhenPresent(record::getToUserId)
                .set(relationType).equalToWhenPresent(record::getRelationType)
                .set(createdTime).equalToWhenPresent(record::getCreatedTime)
                .set(establishedTime).equalToWhenPresent(record::getEstablishedTime)
                .set(status).equalToWhenPresent(record::getStatus);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(WechatUserRelation record) {
        return update(c ->
            c.set(relationType).equalTo(record::getRelationType)
            .set(createdTime).equalTo(record::getCreatedTime)
            .set(establishedTime).equalTo(record::getEstablishedTime)
            .set(status).equalTo(record::getStatus)
            .where(fromUserId, isEqualTo(record::getFromUserId))
            .and(toUserId, isEqualTo(record::getToUserId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(WechatUserRelation record) {
        return update(c ->
            c.set(relationType).equalToWhenPresent(record::getRelationType)
            .set(createdTime).equalToWhenPresent(record::getCreatedTime)
            .set(establishedTime).equalToWhenPresent(record::getEstablishedTime)
            .set(status).equalToWhenPresent(record::getStatus)
            .where(fromUserId, isEqualTo(record::getFromUserId))
            .and(toUserId, isEqualTo(record::getToUserId))
        );
    }
}