package cn.gmwenterprise.presevere.config.security;

import cn.gmwenterprise.presevere.common.BeanHelper;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.service.UserService;

import java.util.List;

public class Authorization {
    private String token;
    private TokenPayload tokenPayload;
    private List<SysPermission> permissions;

    private SysUser currentUser;

    public Authorization() {
    }

    public Authorization(String token, TokenPayload tokenPayload, List<SysPermission> permissions) {
        this.token = token;
        this.tokenPayload = tokenPayload;
        this.permissions = permissions;
    }

    public Authorization(String token, TokenPayload tokenPayload) {
        this.token = token;
        this.tokenPayload = tokenPayload;
    }

    public SysUser currentUser() {
        if (tokenPayload == null || tokenPayload.getUserId() == null) {
            return null;
        }
        if (currentUser == null) {
            currentUser = BeanHelper.getBean(UserService.class).getUserById(tokenPayload.getUserId());
        }
        return currentUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenPayload getTokenPayload() {
        return tokenPayload;
    }

    public void setTokenPayload(TokenPayload tokenPayload) {
        this.tokenPayload = tokenPayload;
    }

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }
}
