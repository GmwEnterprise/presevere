package cn.gmwenterprise.website.generator;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public
class ColumnStruct {
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段对应的数据库类型
     */
    private String columnType;
    /**
     * 字段对应的数据库类型值，对应java.sql.Types
     */
    private int columnTypeNo;
    /**
     * 字段注释
     */
    private String columnComment;
    /**
     * 列对应的成员名
     */
    private String fieldName;
    /**
     * 成员类型
     */
    private String fieldType;
    /**
     * 成员JavaScript类型
     */
    private String javascriptType;
    /**
     * 该字段是否为主键
     */
    private boolean primaryKey;
    /**
     * 该字段是否为自增
     */
    private boolean autoIncrement;

    Map<String, Object> toMap() {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("columnName", columnName);
        map.put("columnType", columnType);
        map.put("columnTypeNo", columnTypeNo);
        map.put("columnComment", columnComment);
        map.put("fieldName", fieldName);
        map.put("fieldType", fieldType);
        map.put("javascriptType", javascriptType);
        map.put("isPrimaryKey", primaryKey);
        map.put("isAutoIncrement", autoIncrement);
        return map;
    }
}
