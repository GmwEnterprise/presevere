package com.example.pmq.mapper;

import static com.example.pmq.mapper.PmqParamDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.example.pmq.domain.PmqParam;
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
public interface PmqParamMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(paramCode, paramDesc, paramValue);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<PmqParam> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<PmqParam> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("PmqParamResult")
    Optional<PmqParam> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="PmqParamResult", value = {
        @Result(column="param_code", property="paramCode", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="param_desc", property="paramDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="param_value", property="paramValue", jdbcType=JdbcType.VARCHAR)
    })
    List<PmqParam> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, pmqParam, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, pmqParam, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String paramCode_) {
        return delete(c -> 
            c.where(paramCode, isEqualTo(paramCode_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(PmqParam record) {
        return MyBatis3Utils.insert(this::insert, record, pmqParam, c ->
            c.map(paramCode).toProperty("paramCode")
            .map(paramDesc).toProperty("paramDesc")
            .map(paramValue).toProperty("paramValue")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<PmqParam> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, pmqParam, c ->
            c.map(paramCode).toProperty("paramCode")
            .map(paramDesc).toProperty("paramDesc")
            .map(paramValue).toProperty("paramValue")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(PmqParam record) {
        return MyBatis3Utils.insert(this::insert, record, pmqParam, c ->
            c.map(paramCode).toPropertyWhenPresent("paramCode", record::getParamCode)
            .map(paramDesc).toPropertyWhenPresent("paramDesc", record::getParamDesc)
            .map(paramValue).toPropertyWhenPresent("paramValue", record::getParamValue)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<PmqParam> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, pmqParam, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<PmqParam> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, pmqParam, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<PmqParam> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, pmqParam, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<PmqParam> selectByPrimaryKey(String paramCode_) {
        return selectOne(c ->
            c.where(paramCode, isEqualTo(paramCode_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, pmqParam, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(PmqParam record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(paramCode).equalTo(record::getParamCode)
                .set(paramDesc).equalTo(record::getParamDesc)
                .set(paramValue).equalTo(record::getParamValue);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(PmqParam record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(paramCode).equalToWhenPresent(record::getParamCode)
                .set(paramDesc).equalToWhenPresent(record::getParamDesc)
                .set(paramValue).equalToWhenPresent(record::getParamValue);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(PmqParam record) {
        return update(c ->
            c.set(paramDesc).equalTo(record::getParamDesc)
            .set(paramValue).equalTo(record::getParamValue)
            .where(paramCode, isEqualTo(record::getParamCode))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(PmqParam record) {
        return update(c ->
            c.set(paramDesc).equalToWhenPresent(record::getParamDesc)
            .set(paramValue).equalToWhenPresent(record::getParamValue)
            .where(paramCode, isEqualTo(record::getParamCode))
        );
    }
}