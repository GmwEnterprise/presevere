package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.config.security.AuthenticationUser;
import cn.gmwenterprise.website.config.security.JwtAuthenticationFilter;
import cn.gmwenterprise.website.config.security.JwtUtils;
import cn.gmwenterprise.website.config.security.User;
import cn.gmwenterprise.website.dao.SysUserDao;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.service.sys.UserTokenService;
import cn.gmwenterprise.website.vo.SysUserVo;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserDao sysUserDao;
    private final UserTokenService userTokenService;

    public SysUserServiceImpl(SysUserDao sysUserDao, UserTokenService userTokenService) {
        this.sysUserDao = sysUserDao;
        this.userTokenService = userTokenService;
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
        User user = userTokenService.generateUser(sysUser);
        AuthenticationUser authentication = new AuthenticationUser(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, Object> payload = Maps.newHashMap();
        payload.put(JwtAuthenticationFilter.KEY_USERNAME, sysUser.getUsername());
        return JwtUtils.createToken(payload);
        // TODO 这段代码待验证
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
