package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.vo.SysUserVo;

import java.util.List;

/**
 * @author gmw
 */
public interface SysUserService {

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
    int insert(SysUserVo vo);

    /**
     * 查询记录
     *
     * @param id 主键
     * @return 结果
     */
    SysUserVo selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     *
     * @param vo 条件
     * @return 结果
     */
    List<SysUserVo> selectPage(SysUserVo vo);

    /**
     * 更新记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(SysUserVo vo);

    /**
     * 创建用户
     * @param nickname 昵称
     * @param username 用户名
     * @param password 密码
     * @return
     */
    AjaxResult createUser(String nickname, String username, String password);
}