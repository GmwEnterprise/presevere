package cn.gmwenterprise.website.config.security;

import cn.gmwenterprise.website.dao.SysUserDao;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.service.sys.UserTokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Resource
    SysUserDao sysUserDao;
    @Resource
    UserTokenService userTokenService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        username = username.trim();
        password = password.trim();
        SysUser sysUser = sysUserDao.selectByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在. username: " + username);
        }
        String encodedPassword = encodePassword(password, sysUser.getSalt());
        if (!encodedPassword.equals(sysUser.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        return new UserToken(userTokenService.generateUser(sysUser));
    }

    private String encodePassword(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
