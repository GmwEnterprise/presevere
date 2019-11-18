package cn.gmwenterprise.website.config.mybatis.typehandler;

import cn.gmwenterprise.website.constants.Sex;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Sex.class)
@MappedJdbcTypes(JdbcType.TINYINT)
public class SexEnum implements TypeHandler<Sex> {
    @Override
    public void setParameter(PreparedStatement p, int i, Sex sex, JdbcType jdbcType) throws SQLException {
        p.setInt(i, sex.getValue());
    }

    @Override
    public Sex getResult(ResultSet rs, String columnName) throws SQLException {
        return Sex.of(rs.getInt(columnName));
    }

    @Override
    public Sex getResult(ResultSet rs, int columnIndex) throws SQLException {
        return Sex.of(rs.getInt(columnIndex));
    }

    @Override
    public Sex getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Sex.of(cs.getInt(columnIndex));
    }
}
