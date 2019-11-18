package cn.gmwenterprise.website.dao;

import cn.gmwenterprise.website.domain.PreArticleBody;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmw
 */
@Repository
public interface PreArticleBodyDao {

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
    int insert(PreArticleBody record);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    PreArticleBody selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param record 条件
     * @return 结果
     */
    List<PreArticleBody> selectPage(PreArticleBody record);

    /**
     * 更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(PreArticleBody record);

    @Select("select * from pre_article_body where article_msg_id = #{id}")
    PreArticleBody selectByMsgId(Integer id);
}