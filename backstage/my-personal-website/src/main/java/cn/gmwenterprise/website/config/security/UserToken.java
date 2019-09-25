package cn.gmwenterprise.website.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class UserToken extends AbstractAuthenticationToken {
    private UserDetails user;

    public UserToken(UserDetails user) {
        super(user.getAuthorities());
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}
