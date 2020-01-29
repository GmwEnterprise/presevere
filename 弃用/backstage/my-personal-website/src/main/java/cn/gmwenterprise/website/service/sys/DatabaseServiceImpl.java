package cn.gmwenterprise.website.service.sys;

import cn.gmwenterprise.website.generator.ColumnStruct;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DatabaseServiceImpl implements DatabaseService {
    private Connection connection;

    private static final String TABLE_LIST_MSG_KEY = "tableListMsg";

    public DatabaseServiceImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public List<TableMsg> getTableList() {
        List<TableMsg> tableListData = null;
        try {
            tableListData = getTableListData();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return tableListData;
    }

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取字段结构
     *
     * @param tableName 指定表名
     * @return 该表所有字段结构集合
     * @throws SQLException 异常
     */
    @Override
    public List<ColumnStruct> getColumnStructList(String tableName) throws Exception {
        Connection conn = dataSource.getConnection();
        DatabaseMetaData dma = connection.getMetaData();
        ResultSet columns = dma.getColumns(conn.getCatalog(), "%", tableName, "%");
        List<ColumnStruct> list = new ArrayList<>();
        String primaryKeyFieldName = getPrimaryKeyFieldName(dma, tableName);
        while (columns.next()) {
            ColumnStruct struct = new ColumnStruct();
            struct.setColumnName(columns.getString("COLUMN_NAME"));
            struct.setColumnComment(columns.getString("REMARKS"));
            struct.setFieldName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, struct.getColumnName()));
            struct.setPrimaryKey(primaryKeyFieldName.equals(struct.getColumnName()));
            list.add(struct);
        }
        columns.close();
        return list;
    }

    private String getPrimaryKeyFieldName(DatabaseMetaData databaseMetaData, String tableName) throws Exception {
        Connection connection = dataSource.getConnection();
        String catalog = connection.getCatalog();
        ResultSet pk = databaseMetaData.getPrimaryKeys(catalog, "%", tableName);
        List<String> pkList = new ArrayList<>();
        while (pk.next()) {
            pkList.add(pk.getString("COLUMN_NAME"));
        }
        pk.close();
        return pkList.get(0);
    }

    private List<TableMsg> getTableListData() throws SQLException {
        PreparedStatement p = connection.prepareStatement("SHOW TABLE STATUS FROM `my_website`");
        ResultSet ts = p.executeQuery();
        List<TableMsg> resultList = Lists.newArrayList();
        while (ts.next()) {
            TableMsg tableMsg = new TableMsg();
            tableMsg.setName(ts.getString("Name"));
            tableMsg.setCreateTime(LocalDateTime.parse(
                ts.getString("Create_time"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            tableMsg.setCollection(ts.getString("Collation"));
            tableMsg.setComment(ts.getString("Comment"));
            resultList.add(tableMsg);
        }
        ts.close();
        p.close();
        return resultList;
    }

    public static class TableMsg {
        private String name;
        private LocalDateTime createTime;
        private String collection;
        private String comment;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
