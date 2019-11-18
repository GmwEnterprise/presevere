package cn.gmwenterprise.website.config.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * 分页插件实现类
 *
 * @author Gmw
 * @date 2019年8月7日 11:16:07
 */
@Slf4j
@Intercepts(
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {
            Connection.class,
            Integer.class
        }
    )
)
public class PageHelper implements Interceptor {

    private static final ThreadLocal<PageInfo> PAGE_INFO = new ThreadLocal<>();
    private static final ThreadLocal<Page<?>> PAGE_RESULT = new ThreadLocal<>();

    /**
     * 开启分页查询，调用该方法后，该线程下下一个查询语句将返回分页对象
     *
     * @param startPage 起始页
     * @param pageSize  本页条数
     */
    public static void startPage(Integer startPage, Integer pageSize) {
        startPage = startPage != null && startPage > 0 ? startPage : 1;
        PAGE_INFO.remove();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(startPage);
        pageInfo.setPageSize(pageSize);
        PAGE_INFO.set(pageInfo);
    }

    @SuppressWarnings("unchecked")
    public static <E> Page<E> page(List<E> list) {
        Page<E> page = (Page<E>) Objects.requireNonNull(PAGE_RESULT.get());
        page.setList(list);
        return page;
    }

    /**
     * 判断是否sql语句
     *
     * @param sql --当前执行SQL
     * @return 是否查询语句
     */
    private boolean checkSelect(String sql) {
        return sql.trim().toLowerCase().indexOf("select") == 0;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1. 检查线程中是否有分页参数，若没有则不启动分页
        PageInfo pageInfo = PAGE_INFO.get();
        if (!defaultUseFlag || pageInfo == null) {
            return invocation.proceed();
        }
        if (pageInfo.getPageSize() == null) {
            pageInfo.setPageSize(defaultPageSize);
        }

        StatementHandler stmtHandler = (StatementHandler) getUnProxyObject(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(stmtHandler);
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        // 不是select语句
        if (!checkSelect(sql)) {
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");

        if (!defaultUseFlag) {
            // 不使用分页插件
            return invocation.proceed();
        }

        Page<?> page = new Page<>();

        // 计算总条数
        int total = getTotal(invocation, metaStatementHandler, boundSql);
        // 回填总条数到分页参数
        page.setTotal(total);
        // 计算总页数.
        int totalPage = total % pageInfo.getPageSize() == 0 ? total / pageInfo.getPageSize() : total / pageInfo.getPageSize() + 1;
        // 回填总页数到分页参数
        page.setTotalPage(totalPage);
        // 检查当前页码的有效性
        // 4. 检测分页参数是否合法
        if (pageInfo.getCurrentPage() > totalPage) {
            log.warn("分页参数不合法！输入页码为[{}], 总页数为[{}]", pageInfo.getCurrentPage(), totalPage);
            // 设置为最后一页
            pageInfo.setCurrentPage(totalPage > 0 ? totalPage : 1);
        }

        page.setCurrentPage(pageInfo.getCurrentPage());
        page.setPageSize(pageInfo.getPageSize());
        page.setHasNextPage(pageInfo.getCurrentPage() < totalPage);
        page.setHasPrevPage(pageInfo.getCurrentPage() > 1);

        PAGE_RESULT.remove();
        PAGE_RESULT.set(page);
        // 修改sql
        return preparedSql(invocation, metaStatementHandler, boundSql, pageInfo.getCurrentPage(), pageInfo.getPageSize());
    }


    /**
     * 预编译改写后的SQL，并设置分页参数
     *
     * @param invocation           入参
     * @param metaStatementHandler MetaObject绑定的StatementHandler
     * @param boundSql             boundSql对象
     * @param pageNum              当前页
     * @param pageSize             最大页
     * @throws Exception 异常
     */
    private Object preparedSql(Invocation invocation, MetaObject metaStatementHandler, BoundSql boundSql, int pageNum,
                               int pageSize) throws Exception {
        // 获取当前需要执行的SQL
        String sql = boundSql.getSql();
        String newSql = "select * from (" + sql + ") $_paging_table limit ?, ?";
        // 修改当前需要执行的SQL
        metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
        // 执行编译，相当于StatementHandler执行了prepared()方法，这个时候，就剩下两个分页参数没有设置
        Object statementObj = invocation.proceed();
        // 设置两个分页参数
        this.preparePageDataParams((PreparedStatement) statementObj, pageNum, pageSize);
        return statementObj;
    }


    /**
     * 使用PreparedStatement预编译两个分页参数，如果数据库的规则不一样，需要改写设置的参数规则
     *
     * @throws Exception 异常
     */
    private void preparePageDataParams(PreparedStatement ps, int pageNum, int pageSize) throws Exception {
        // prepared()方法编译SQL，由于MyBatis上下文没有分页参数的信息，所以这里需要设置这两个参数
        // 获取需要设置的参数个数，由于参数是最后的两个，所以很容易得到其位置
        int idx = ps.getParameterMetaData().getParameterCount();
        // 最后两个是我们的分页参数
        // 开始行
        ps.setInt(idx - 1, (pageNum - 1) * pageSize);
        // 限制条数
        ps.setInt(idx, pageSize);
    }

    /**
     * 获取总条数.
     *
     * @param ivt                  Invocation 入参
     * @param metaStatementHandler statementHandler
     * @param boundSql             sql
     * @return sql查询总数.
     * @throws Throwable 异常.
     */
    private int getTotal(Invocation ivt, MetaObject metaStatementHandler, BoundSql boundSql)
        throws Throwable {
        // 获取当前的mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // 配置对象
        Configuration cfg = mappedStatement.getConfiguration();
        // 当前需要执行的SQL
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        // 改写为统计总数的SQL
        String countSql = String.format("select count(*) as total from (%s) $_paging", sql);
        // 获取拦截方法参数，根据插件签名，知道是Connection对象
        Connection connection = (Connection) ivt.getArgs()[0];
        PreparedStatement ps = null;
        int total = 0;
        try {
            // 预编译统计总数SQL
            ps = connection.prepareStatement(countSql);
            // 构建统计总数BoundSql
            BoundSql countBoundSql = new BoundSql(cfg, countSql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());
            // 构建MyBatis的ParameterHandler用来设置总数Sql的参数
            ParameterHandler handler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(),
                countBoundSql);
            // 设置总数SQL参数
            handler.setParameters(ps);
            // 执行查询.
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } finally {
            // 这里不能关闭Connection，否则后续的SQL就没法继续了
            if (ps != null) {
                ps.close();
            }
        }
        return total;
    }

    /**
     * 从代理对象中分离出真实对象
     *
     * @param target 代理对象
     * @return 非代理对象
     */
    private Object getUnProxyObject(Object target) {
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object object = null;
        while (metaObject.hasGetter("h")) {
            object = metaObject.getValue("h");
            metaObject = SystemMetaObject.forObject(object);
        }
        return object == null ? target : object;
    }

    /**
     * 默认每页长度
     */
    private int defaultPageSize;
    /**
     * 是否启用插件
     */
    private boolean defaultUseFlag;

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 初始化插件参数，源自于配置文件中提供的参数
     *
     * @param properties 配置的参数
     */
    @Override
    public void setProperties(Properties properties) {
        defaultPageSize = Integer.parseInt(Optional.ofNullable(properties.getProperty("default.pageSize")).orElse("20"));
        defaultUseFlag = Boolean.parseBoolean(Optional.ofNullable(properties.getProperty("default.useFlag")).orElse("true"));
    }
}
