package cn.gmwenterprise.presevere.common;

import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.vo.Authentication;

public class Authorization {
    private String token;
    private SysUser currentUser;
    private Authentication authentication;

    public Authorization() {
    }

    public Authorization(String token, SysUser currentUser, Authentication authentication) {
        this.token = token;
        this.currentUser = currentUser;
        this.authentication = authentication;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SysUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(SysUser currentUser) {
        this.currentUser = currentUser;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
