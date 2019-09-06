package cn.gmwenterprise.website.service.sys;

import cn.gmwenterprise.website.generator.ColumnStruct;

import java.util.List;

public interface DatabaseService {

    List<DatabaseServiceImpl.TableMsg> getTableList();

    List<ColumnStruct> getColumnStructList(String tableName) throws Exception;
}
