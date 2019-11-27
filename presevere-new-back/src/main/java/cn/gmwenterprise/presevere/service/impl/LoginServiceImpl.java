package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.BusinessException;
import cn.gmwenterprise.presevere.common.Constants;
import cn.gmwenterprise.presevere.common.Role;
import cn.gmwenterprise.presevere.common.TokenHelper;
import cn.gmwenterprise.presevere.config.security.Authentication;
import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import cn.gmwenterprise.presevere.service.RoleService;
import cn.gmwenterprise.presevere.vo.LoginSuccess;
import cn.gmwenterprise.presevere.vo.UsernameValidationResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public LoginSuccess register(HttpServletRequest request, DtoSign body) {
        // 插入新用户
        SysUser user = randomSalt(body.getLoginName());
        // 获取密码
        String password = aesDecode(body.getPassword());
        user.setPassword(passwordEncoder.encode(password));
        sysUserMapper.insertSelective(user);

        // 初始化用户角色信息
        roleService.setRoles(user.getId(), Role.USER.getRole());

        // 注册成功
        return loginSuccess(request, user.getId(), body.getKeepLogin());
    }

    private String aesDecode(String password) {
        // TODO AES解密
        return password;
    }

    @Override
    public LoginSuccess login(DtoSign body, HttpServletRequest request) {
        String username = body.getLoginName();
        String password = body.getPassword();

        SysUser sysUser = sysUserMapper.selectByUsername(username);
        if (sysUser == null) {
            throw new BusinessException("用户不存在，请重新输入！");
        }
        boolean passwordEquals;
        if (!StringUtils.isEmpty(sysUser.getSalt())) {
            passwordEquals = passwordEncoder.matches(password, sysUser.getPassword());
        } else {
            passwordEquals = password.equals(sysUser.getPassword());
        }
        if (passwordEquals) {
            // 登录成功
            return loginSuccess(request, sysUser.getId(), body.getKeepLogin());
        }
        throw new BusinessException("密码错误，请重新输入！");
    }

    private LoginSuccess loginSuccess(HttpServletRequest request, Integer userId, Boolean keepLogin) {
        Authentication payload = new Authentication(
            LocalDateTime.now(), request.getRemoteHost(),
            userId, Authentication.Platform.BROWSER, keepLogin ? 0L : Constants.DEFAULT_TOKEN_TIMEOUT
        );
        return new LoginSuccess(TokenHelper.generateToken(payload));
    }

    @Override
    public void logout() {
        // do nothing here
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
        return user;
    }

    @Override
    public UsernameValidationResult verifyUsername(String username, int mode) {
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        if (mode == LOGIN) {
            if (sysUser == null) {
                throw new BusinessException("用户不存在，请重新输入！");
            }
            return UsernameValidationResult.valid();
        } else if (mode == REGISTRY) {
            if (sysUser != null && sysUser.getId() != null) {
                throw new BusinessException("用户名已存在");
            }
            return UsernameValidationResult.valid();
        }
        throw new RuntimeException("程序异常，mode值错误！");
    }
}
