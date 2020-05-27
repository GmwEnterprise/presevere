package com.github.mrag.wechat.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(EnumStatus.class)
@MappedJdbcTypes(JdbcType.TINYINT)
public class EnumStatusHandler implements TypeHandler<EnumStatus> {
    @Override
    public void setParameter(PreparedStatement ps, int i, EnumStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getStatus());
    }

    @Override
    public EnumStatus getResult(ResultSet rs, String columnName) throws SQLException {
        return EnumStatus.get(rs.getInt(columnName));
    }

    @Override
    public EnumStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        return EnumStatus.get(rs.getInt(columnIndex));
    }

    @Override
    public EnumStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EnumStatus.get(cs.getInt(columnIndex));
    }
}
