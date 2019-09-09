package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.domain.SysRole;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.vo.SysUserRoleVo;

import java.util.List;

/**
 * @author gmw
 */
public interface SysUserRoleService {

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
    int insert(SysUserRoleVo vo);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    SysUserRoleVo selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param vo 条件
     * @return 结果
     */
    List<SysUserRoleVo> selectPage(SysUserRoleVo vo);

    /**
     * 更新记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(SysUserRoleVo vo);

    /**
     * 获取某个用户的全部角色
     *
     * @param user 用户
     * @return 角色列表
     */
    List<SysRole> getRoleByUser(SysUser user);
}