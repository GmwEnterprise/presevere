package cn.gmwenterprise.presevere.config.security;

import cn.gmwenterprise.presevere.common.BeanHelper;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.service.UserService;

public class Authorization {
    private String token;
    private TokenPayload tokenPayload;

    public Authorization() {
    }

    public Authorization(String token, TokenPayload tokenPayload) {
        this.token = token;
        this.tokenPayload = tokenPayload;
    }

    public SysUser currentUser() {
        if (tokenPayload == null || tokenPayload.getUserId() == null) {
            return null;
        }
        return BeanHelper.getBean(UserService.class).getUserById(tokenPayload.getUserId());
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
}
