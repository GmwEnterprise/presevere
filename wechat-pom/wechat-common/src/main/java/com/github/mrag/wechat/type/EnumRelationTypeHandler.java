package com.github.mrag.wechat.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(EnumRelationType.class)
@MappedJdbcTypes(JdbcType.TINYINT)
public class EnumRelationTypeHandler implements TypeHandler<EnumRelationType> {
    @Override
    public void setParameter(PreparedStatement ps, int i, EnumRelationType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public EnumRelationType getResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return EnumRelationType.get(value);
    }

    @Override
    public EnumRelationType getResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return EnumRelationType.get(value);
    }

    @Override
    public EnumRelationType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return EnumRelationType.get(value);
    }
}
