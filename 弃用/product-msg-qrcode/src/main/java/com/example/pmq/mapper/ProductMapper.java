package com.example.pmq.mapper;

import static com.example.pmq.mapper.ProductDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.example.pmq.domain.Product;
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
public interface ProductMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, productId, productName, productDesc, productCreatedTime, productQrcode);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Product> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Product> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ProductResult")
    Optional<Product> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ProductResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="product_id", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="product_name", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="product_desc", property="productDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="product_created_time", property="productCreatedTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="product_qrcode", property="productQrcode", jdbcType=JdbcType.LONGVARBINARY)
    })
    List<Product> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, product, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, product, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(Product record) {
        return MyBatis3Utils.insert(this::insert, record, product, c ->
            c.map(id).toProperty("id")
            .map(productId).toProperty("productId")
            .map(productName).toProperty("productName")
            .map(productDesc).toProperty("productDesc")
            .map(productCreatedTime).toProperty("productCreatedTime")
            .map(productQrcode).toProperty("productQrcode")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<Product> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, product, c ->
            c.map(id).toProperty("id")
            .map(productId).toProperty("productId")
            .map(productName).toProperty("productName")
            .map(productDesc).toProperty("productDesc")
            .map(productCreatedTime).toProperty("productCreatedTime")
            .map(productQrcode).toProperty("productQrcode")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(Product record) {
        return MyBatis3Utils.insert(this::insert, record, product, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(productId).toPropertyWhenPresent("productId", record::getProductId)
            .map(productName).toPropertyWhenPresent("productName", record::getProductName)
            .map(productDesc).toPropertyWhenPresent("productDesc", record::getProductDesc)
            .map(productCreatedTime).toPropertyWhenPresent("productCreatedTime", record::getProductCreatedTime)
            .map(productQrcode).toPropertyWhenPresent("productQrcode", record::getProductQrcode)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Product> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, product, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Product> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, product, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Product> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, product, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Product> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, product, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(Product record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(productId).equalTo(record::getProductId)
                .set(productName).equalTo(record::getProductName)
                .set(productDesc).equalTo(record::getProductDesc)
                .set(productCreatedTime).equalTo(record::getProductCreatedTime)
                .set(productQrcode).equalTo(record::getProductQrcode);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Product record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(productId).equalToWhenPresent(record::getProductId)
                .set(productName).equalToWhenPresent(record::getProductName)
                .set(productDesc).equalToWhenPresent(record::getProductDesc)
                .set(productCreatedTime).equalToWhenPresent(record::getProductCreatedTime)
                .set(productQrcode).equalToWhenPresent(record::getProductQrcode);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(Product record) {
        return update(c ->
            c.set(productId).equalTo(record::getProductId)
            .set(productName).equalTo(record::getProductName)
            .set(productDesc).equalTo(record::getProductDesc)
            .set(productCreatedTime).equalTo(record::getProductCreatedTime)
            .set(productQrcode).equalTo(record::getProductQrcode)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(Product record) {
        return update(c ->
            c.set(productId).equalToWhenPresent(record::getProductId)
            .set(productName).equalToWhenPresent(record::getProductName)
            .set(productDesc).equalToWhenPresent(record::getProductDesc)
            .set(productCreatedTime).equalToWhenPresent(record::getProductCreatedTime)
            .set(productQrcode).equalToWhenPresent(record::getProductQrcode)
            .where(id, isEqualTo(record::getId))
        );
    }
}