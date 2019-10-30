package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.vo.PreArticle;
import cn.gmwenterprise.website.vo.PreArticleMsgVo;

import java.util.List;

/**
 * @author gmw
 */
public interface PreArticleMsgService {

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
     * @param vo 记录
     * @return 受影响行数
     */
    int insert(PreArticleMsgVo vo);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    PreArticleMsgVo selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param vo 条件
     * @return 结果
     */
    List<PreArticleMsgVo> selectPage(PreArticleMsgVo vo);

    /**
     * 更新记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(PreArticleMsgVo vo);

    PreArticle getArticleById(Integer id);
}