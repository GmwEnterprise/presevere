package com.example.pmq.mapper;

import java.sql.JDBCType;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ProductDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final ProductTB product = new ProductTB();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = product.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> productId = product.productId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> productName = product.productName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> productDesc = product.productDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<LocalDateTime> pruductCreatedTime = product.pruductCreatedTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<byte[]> productQrcode = product.productQrcode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class ProductTB extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> productId = column("product_id", JDBCType.VARCHAR);

        public final SqlColumn<String> productName = column("product_name", JDBCType.VARCHAR);

        public final SqlColumn<String> productDesc = column("product_desc", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> pruductCreatedTime = column("pruduct_created_time", JDBCType.TIMESTAMP);

        public final SqlColumn<byte[]> productQrcode = column("product_qrcode", JDBCType.LONGVARBINARY);

        public ProductTB() {
            super("product");
        }
    }
}