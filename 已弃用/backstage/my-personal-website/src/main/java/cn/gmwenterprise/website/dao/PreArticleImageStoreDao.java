package cn.gmwenterprise.website.dao;

import cn.gmwenterprise.website.domain.PreArticleImageStore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmw
 */
@Repository
public interface PreArticleImageStoreDao {

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
    int insert(PreArticleImageStore record);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    PreArticleImageStore selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param record 条件
     * @return 结果
     */
    List<PreArticleImageStore> selectPage(PreArticleImageStore record);

    /**
     * 更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(PreArticleImageStore record);
}