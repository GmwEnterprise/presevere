package cn.gmwenterprise.website.config.security;

import cn.gmwenterprise.website.service.sys.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component("jwtUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserTokenService userTokenService;

    public UserDetailsServiceImpl(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userTokenService.generateUser(username);
        } catch (Throwable t) {
            log.error("用户名查询错误", t);
            throw new UsernameNotFoundException("未找到用户: " + t.getMessage());
        }
    }
}
