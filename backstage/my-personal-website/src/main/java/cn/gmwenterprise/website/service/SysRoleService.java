package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.vo.SysRoleVo;

import java.util.List;

/**
 * @author gmw
 */
public interface SysRoleService {

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
    int insert(SysRoleVo vo);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    SysRoleVo selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param vo 条件
     * @return 结果
     */
    List<SysRoleVo> selectPage(SysRoleVo vo);

    /**
     * 更新记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(SysRoleVo vo);
}