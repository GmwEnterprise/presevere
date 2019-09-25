package cn.gmwenterprise.website.dao;

import cn.gmwenterprise.website.domain.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gmw
 */
@Repository
public interface SysRoleDao {

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
    int insert(SysRole record);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    SysRole selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param record 条件
     * @return 结果
     */
    List<SysRole> selectPage(SysRole record);

    /**
     * 更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(SysRole record);

    /**
     * 获取指定用户的所有角色
     * @param userId 指定用户
     * @return 角色列表
     */
    List<SysRole> selectRoleListByUser(Integer userId);
}