package cn.gmwenterprise.website.generator;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DatabaseHelper {

    private Connection conn;
    private DatabaseMetaData databaseMetaData;

    private DatabaseHelper() throws Exception {
        String url = "jdbc:mysql://120.79.155.125:3306/my_website?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
        String user = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
        databaseMetaData = conn.getMetaData();
    }

    /**
     * 关闭数据库连接
     *
     * @throws SQLException 异常
     */
    public static void close() throws SQLException {
        if (instance != null) {
            instance.conn.close();
        }
        instance = null;
    }

    private static DatabaseHelper instance = null;

    /**
     * 获取数据库连接帮助对象实例
     *
     * @return 实例
     * @throws Exception 异常
     */
    public static DatabaseHelper getInstance() throws Exception {
        if (instance == null) {
            instance = new DatabaseHelper();
        }
        return instance;
    }

    /**
     * 获取指定表结构
     *
     * @param tableName 指定表名
     * @throws SQLException 异常
     */
    public TableStruct getTableStruct(String tableName) throws Exception {
        ResultSet trs = databaseMetaData.getTables(conn.getCatalog(), "%", tableName, new String[]{"TABLE"});
        String pk = getPrimaryKeyFieldName(databaseMetaData, tableName);
        List<TableStruct> tableStructs = new ArrayList<>();
        while (trs.next()) {
            TableStruct tableStruct = new TableStruct();
            tableStruct.setTableName(trs.getString("TABLE_NAME"));
            tableStruct.setTableComment(trs.getString("REMARKS"));
            List<ColumnStruct> columns = getColumnStructList(tableStruct.getTableName());
            ColumnStruct key = columns.stream()
                .filter(ColumnStruct::isPrimaryKey)
                .collect(Collectors.toList())
                .get(0);
            tableStruct.setColumnList(columns);
            tableStruct.setEntityName(nameConversion(tableStruct.getTableName(), 2));
            tableStruct.setEntityAlias(nameConversion(tableStruct.getTableName(), 1));
            tableStruct.setKeyProperty(nameConversion(pk, 1));
            tableStruct.setKeyPropertyType(key.getFieldType());
            tableStruct.setKeyColumn(pk);
            tableStruct.setKeyColumnType(key.getColumnType());
            tableStructs.add(tableStruct);
            if (tableStructs.size() > 0) {
                break;
            }
        }
        return tableStructs.size() > 0 ? tableStructs.get(0) : null;
    }

    /**
     * 获取字段结构
     *
     * @param tableName 指定表名
     * @return 该表所有字段结构集合
     * @throws SQLException 异常
     */
    private List<ColumnStruct> getColumnStructList(String tableName) throws Exception {
        ResultSet columns = databaseMetaData.getColumns(conn.getCatalog(), "%", tableName, "%");
        String pk = getPrimaryKeyFieldName(databaseMetaData, tableName);
        List<ColumnStruct> list = new ArrayList<>();
        while (columns.next()) {
            ColumnStruct struct = new ColumnStruct();
            struct.setColumnName(columns.getString("COLUMN_NAME"));
            struct.setColumnComment(columns.getString("REMARKS"));
            struct.setColumnTypeNo(columns.getInt("DATA_TYPE"));
            struct.setColumnType(JdbcType.forCode(struct.getColumnTypeNo()).name());
            struct.setFieldName(nameConversion(struct.getColumnName(), 1));
            struct.setFieldType(typeConversion(struct.getColumnTypeNo()));
            struct.setJavascriptType(javascriptTypeConversion(struct.getColumnTypeNo()));
            struct.setAutoIncrement("YES".equals(columns.getString("IS_AUTOINCREMENT")));
            struct.setPrimaryKey(pk.equals(struct.getColumnName()));
            list.add(struct);
        }
        return list;
    }

    private String getPrimaryKeyFieldName(DatabaseMetaData databaseMetaData, String tableName) throws Exception {
        ResultSet pk = databaseMetaData.getPrimaryKeys(conn.getCatalog(), "%", tableName);
        List<String> pkList = Lists.newArrayList();
        while (pk.next()) {
            pkList.add(pk.getString("COLUMN_NAME"));
        }
        if (pkList.size() > 1) {
            throw new Exception("代码生成器只支持表中有且只有一个主键，不支持多主键存在！");
        }
        return pkList.get(0);
    }

    /**
     * 名称转换
     *
     * @param original 字段名
     * @param type     转换类型，1=小写开头驼峰，2=大写开头驼峰
     * @return 转换后结果
     */
    private String nameConversion(String original, int type) {
        switch (type) {
            case 1:
                // table_name => tableName
                String[] strings = original.split("_");
                if (strings.length == 1) {
                    return original;
                }
                String collect = Arrays.stream(strings)
                    .map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))
                    .collect(Collectors.joining());
                char[] chars = collect.toCharArray();
                chars[0] += 32;
                return String.valueOf(chars);
            case 2:
                // table_name => TableName
                String[] string2 = original.split("_");
                if (string2.length == 1) {
                    char[] chars1 = original.toCharArray();
                    chars1[0] -= 32;
                    return String.valueOf(chars1);
                }
                return Arrays.stream(string2)
                    .map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))
                    .collect(Collectors.joining());
            default:
                return null;
        }
    }

    /**
     * JDBC类型转Java类型
     *
     * @param dataType JDBC类型编号
     * @return Java类型字符串
     */
    private String typeConversion(int dataType) {
        switch (dataType) {
            case -7:
                // jdbcType = BIT
                return "Boolean";
            case -6:
            case 5:
            case 4:
            case -5:
                return "Integer";
            case 1:
            case 12:
            case -1:
            case -9:
            case -15:
                return "String";
            case 91:
                return "LocalDate";
            case 92:
                return "LocalTime";
            case 93:
                return "LocalDateTime";
            default:
                return "Object";
        }
    }

    private String javascriptTypeConversion(int columnTypeNo) {
        switch (columnTypeNo) {
            case -6:
            case 5:
            case 4:
            case -5:
                return "number";
            case 1:
            case 12:
            case -1:
            case -9:
            case -15:
                return "string";
            case 91:
            case 92:
            case 93:
                return "date";
            default:
                return "any";
        }
    }

    public static void main(String[] args) throws Exception {
    }

    @SuppressWarnings("unchecked")
    public static void main1() throws Exception {
        DatabaseHelper.getInstance()
            .getTableStruct("account")
            .toMap()
            .forEach((key, value) -> {
                if (value instanceof Iterable) {
                    System.out.println(key + ": [");
                    ((List) value).forEach(listItem -> System.out.println("    " + listItem));
                    System.out.println("]");
                } else {
                    System.out.println(key + ": " + value);
                }
            });
    }
}
