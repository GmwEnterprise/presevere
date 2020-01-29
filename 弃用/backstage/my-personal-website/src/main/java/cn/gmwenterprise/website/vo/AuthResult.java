package cn.gmwenterprise.website.vo;

import cn.gmwenterprise.website.domain.SysUser;

/**
 * 鉴权成功后返回的数据结构
 */
public class AuthResult {
    private SysUser userMessage;
    private String token;

    public AuthResult() {
    }

    public AuthResult(SysUser userMessage, String token) {
        this.userMessage = userMessage;
        this.token = token;
    }

    /**
     * @return 用户信息
     */
    public SysUser getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(SysUser userMessage) {
        this.userMessage = userMessage;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
