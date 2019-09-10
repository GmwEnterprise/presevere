package cn.gmwenterprise.website.service.sys;

import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.service.SysUserRoleService;
import cn.gmwenterprise.website.service.SysUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("customizeUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService userService;
    private final SysUserRoleService userRoleService;

    public UserDetailsServiceImpl(SysUserService userService, SysUserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = Optional
            .ofNullable(userService.selectByUserName(userName))
            .orElseThrow(() -> new UsernameNotFoundException("用户名不存在！"));
        List<SimpleGrantedAuthority> authorityList = userRoleService.getRoleByUser(user)
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
            .collect(Collectors.toList());
        return new User(user.getUserName(), user.getPwd(), authorityList);
    }
}
