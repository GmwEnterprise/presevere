package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.BusinessException;
import cn.gmwenterprise.presevere.common.Role;
import cn.gmwenterprise.presevere.common.TokenHelper;
import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.dao.SysUserRoleMapper;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import cn.gmwenterprise.presevere.service.RoleService;
import cn.gmwenterprise.presevere.vo.Authentication;
import cn.gmwenterprise.presevere.vo.LoginSuccess;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    RoleService roleService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginSuccess register(HttpServletRequest request, String username, String password) {
        SysUser user = sysUserMapper.selectByUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        SysUser tobeUpdated = new SysUser();
        tobeUpdated.setId(user.getId());
        tobeUpdated.setPassword(user.getPassword());
        sysUserMapper.updateByPrimaryKeySelective(tobeUpdated);

        // 初始化用户角色信息
        roleService.setRoles(user.getId(), Role.USER.getRole());

        // 注册成功，生成登录后需要的token
        Authentication payload = new Authentication(
            LocalDateTime.now(), request.getRemoteHost(),
            user.getId(), Authentication.Platform.BROWSER, 0L
        );
        return new LoginSuccess(TokenHelper.generateToken(payload));
    }

    @Override
    public String login(DtoSign body) {
        String username = body.getLoginName();
        String password = body.getPassword();
        // TODO
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public SysUser randomSalt(String username) {
        if (sysUserMapper.countByUsername(username) > 0) {
            throw new BusinessException("该用户名已经被使用！");
        }
        byte[] bytes = new byte[12];
        new SecureRandom(username.getBytes()).nextBytes(bytes);
        String salt = Base64.getEncoder().encodeToString(bytes);
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setSalt(salt);
        sysUserMapper.insertSelective(user);
        return user;
    }
}
