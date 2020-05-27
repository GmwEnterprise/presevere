package com.example.pmq.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class PmqParamDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final PmqParam pmqParam = new PmqParam();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> paramCode = pmqParam.paramCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> paramDesc = pmqParam.paramDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> paramValue = pmqParam.paramValue;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class PmqParam extends SqlTable {
        public final SqlColumn<String> paramCode = column("param_code", JDBCType.VARCHAR);

        public final SqlColumn<String> paramDesc = column("param_desc", JDBCType.VARCHAR);

        public final SqlColumn<String> paramValue = column("param_value", JDBCType.VARCHAR);

        public PmqParam() {
            super("pmq_param");
        }
    }
}