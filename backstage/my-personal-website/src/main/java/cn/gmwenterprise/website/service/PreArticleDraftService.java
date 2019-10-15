package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.vo.PreArticleDraftVo;

import java.util.List;

/**
 * @author gmw
 */
public interface PreArticleDraftService {

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
    int insert(PreArticleDraftVo vo);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    PreArticleDraftVo selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param vo 条件
     * @return 结果
     */
    List<PreArticleDraftVo> selectPage(PreArticleDraftVo vo);

    /**
     * 更新记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(PreArticleDraftVo vo);
}