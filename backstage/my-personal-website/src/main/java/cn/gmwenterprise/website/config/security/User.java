package cn.gmwenterprise.website.config.security;

import cn.gmwenterprise.website.domain.SysRole;
import cn.gmwenterprise.website.domain.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    private SysUser user;
    private List<SysRole> userRoles;

    public User(SysUser user) {
        // TODO 初始化user和userRoles
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAvailable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAvailable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getAvailable();
    }

    @Override
    public boolean isEnabled() {
        return user.getAvailable();
    }
}
