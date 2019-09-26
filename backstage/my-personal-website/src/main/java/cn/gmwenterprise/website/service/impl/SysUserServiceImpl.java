package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.config.security.JwtAuthenticationFilter;
import cn.gmwenterprise.website.config.security.JwtUtils;
import cn.gmwenterprise.website.dao.SysRoleDao;
import cn.gmwenterprise.website.dao.SysUserDao;
import cn.gmwenterprise.website.dao.SysUserRoleDao;
import cn.gmwenterprise.website.domain.SysRole;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.domain.SysUserRole;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.vo.SysUserVo;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserDao sysUserDao;
    private final SysUserRoleDao sysUserRoleDao;
    private final SysRoleDao sysRoleDao;

    public SysUserServiceImpl(SysUserDao sysUserDao, SysUserRoleDao sysUserRoleDao, SysRoleDao sysRoleDao) {
        this.sysUserDao = sysUserDao;
        this.sysUserRoleDao = sysUserRoleDao;
        this.sysRoleDao = sysRoleDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysUserDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserVo vo) {
        return sysUserDao.insert(domain(vo));
    }

    @Override
    public SysUserVo selectByPrimaryKey(Integer id) {
        return vo(sysUserDao.selectByPrimaryKey(id));
    }

    @Override
    public List<SysUserVo> selectPage(SysUserVo vo) {
        return sysUserDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(SysUserVo vo) {
        return sysUserDao.updateByPrimaryKey(domain(vo));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public AjaxResult createUser(String nickname, String username, String password) {
        // TODO 验证注册参数
        SysUser sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        int result = sysUserDao.insert(sysUser);
        return result > 0 ? AjaxResult.ok(createUserToken(sysUser)) : AjaxResult.fail("注册失败");
    }

    /**
     * 注册成功后创建token并返回.<br/>
     * 同时初始化新用户的基本权限并构建UserDetails实体
     *
     * @param sysUser 注册成功后的用户信息
     * @return token字符串
     */
    private String createUserToken(SysUser sysUser) {
        // 找出USER角色的数据库记录
        SysRole sysRole = sysRoleDao.selectRoleByName("ROLE_USER");
        // 构建user、role关系
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(sysUser.getId());
        userRole.setRoleId(sysRole.getId());
        sysUserRoleDao.insert(userRole);
        // 注册成功返回鉴权token
        Map<String, Object> payload = Maps.newHashMap();
        payload.put(JwtAuthenticationFilter.KEY_USERNAME, sysUser.getUsername());
        return JwtUtils.createToken(payload);
    }

    private SysUserVo vo(SysUser domain) {
        if (domain == null) {
            return null;
        }
        SysUserVo vo = new SysUserVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private SysUser domain(SysUserVo vo) {
        if (vo == null) {
            return null;
        }
        SysUser domain = new SysUser();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
