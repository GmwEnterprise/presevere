package cn.gmwenterprise.website.dao;

import cn.gmwenterprise.website.domain.SysRouter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmw
 */
@Repository
public interface SysRouterDao {

    /**
     * 删除记录
     *
     * @param id 主键
     * @return 受影响行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    int insert(SysRouter record);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    SysRouter selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param record 条件
     * @return 结果
     */
    List<SysRouter> selectPage(SysRouter record);

    /**
     * 更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(SysRouter record);
}