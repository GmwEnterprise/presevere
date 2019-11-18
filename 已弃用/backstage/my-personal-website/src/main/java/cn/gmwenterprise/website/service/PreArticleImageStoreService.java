package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.vo.ImageStoreVo;

import java.util.List;

/**
 * @author gmw
 */
public interface PreArticleImageStoreService {

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
    int insert(ImageStoreVo vo);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    ImageStoreVo selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param vo 条件
     * @return 结果
     */
    List<ImageStoreVo> selectPage(ImageStoreVo vo);

    /**
     * 更新记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(ImageStoreVo vo);

    String uploadImage(byte[] imageBytes, String imageName);
}