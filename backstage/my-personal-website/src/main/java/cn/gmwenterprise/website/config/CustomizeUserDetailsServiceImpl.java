package cn.gmwenterprise.website.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customizeUserDetailsService")
public class CustomizeUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String adminUserName = "admin";
        if (!adminUserName.equals(userName)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(adminUserName, "123", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    }
}
