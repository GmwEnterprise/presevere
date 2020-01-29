package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.vo.PreArticle;
import cn.gmwenterprise.website.vo.PreArticleDraftVo;
import cn.gmwenterprise.website.vo.PreArticleMsgVo;

import java.util.List;
import java.util.Map;

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

    /**
     * 重新编辑已发布的文章<br/>
     * <br/>
     * 需要注意的是，重新编辑文章存在的问题：<br/>
     * 1. 重新编辑可以放弃<br/>
     * 2. 未发布新版本时原先版本依旧可以查阅<br/>
     * 3. 编辑仍然可以保存<br/>
     * <br/>
     * 实现思路：通过传入的msgId获取到当前版本文章的草稿全内容，复制新增一个新版本的草稿，
     * 设置版本号加1；当发布新版本时，直接修改msg、body
     * @param msgId 传入的msgId
     * @return 新版本草稿主键
     */
    Integer reEdit(Integer msgId);

    Map<String, Integer> queryAllTags();
}