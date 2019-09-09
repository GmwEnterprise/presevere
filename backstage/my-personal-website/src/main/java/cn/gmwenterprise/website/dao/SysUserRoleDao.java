package cn.gmwenterprise.website.dao;

import cn.gmwenterprise.website.domain.SysRole;
import cn.gmwenterprise.website.domain.SysUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmw
 */
@Repository
public interface SysUserRoleDao {

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
    int insert(SysUserRole record);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    SysUserRole selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param record 条件
     * @return 结果
     */
    List<SysUserRole> selectPage(SysUserRole record);

    /**
     * 更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(SysUserRole record);

    /**
     * 获取用户的角色列表
     *
     * @param userId 用户主键
     * @return 角色列表
     */
    List<SysRole> selectRoleByUserId(Integer userId);
}