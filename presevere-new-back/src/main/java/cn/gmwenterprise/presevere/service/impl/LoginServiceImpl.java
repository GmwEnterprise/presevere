package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.BusinessException;
import cn.gmwenterprise.presevere.common.Role;
import cn.gmwenterprise.presevere.common.TokenHelper;
import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import cn.gmwenterprise.presevere.service.RoleService;
import cn.gmwenterprise.presevere.vo.Authentication;
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
            Authentication payload = new Authentication(
                LocalDateTime.now(), request.getRemoteHost(),
                sysUser.getId(), Authentication.Platform.BROWSER, 0L
            );
            return new LoginSuccess(TokenHelper.generateToken(payload));
        }
        throw new BusinessException("密码错误，请重新输入！");
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

    @Override
    public UsernameValidationResult validUsername(String username) {
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        if (sysUser == null) {
            throw new BusinessException("用户不存在，请重新输入！");
        }
        UsernameValidationResult validationResult = new UsernameValidationResult();
        validationResult.setFrontEncoded(!StringUtils.isEmpty(sysUser.getSalt()));
        validationResult.setSalt(sysUser.getSalt());
        return validationResult;
    }

//    public static void main(String[] args) {
//        String enc = "$2a$10$DoPkQM1.NBtWur/1gPvhEu/ciw9L0838N0cxfObDteOA53hc8R0a.";
//        BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
//        System.out.println(pass.matches("639a0f49eed850e39f134943c4c37624", enc));
//    }
}
