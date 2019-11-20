package cn.gmwenterprise.presevere.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 登录凭据
 */
public class Authentication {
    public Authentication() {
    }

    public Authentication(LocalDateTime loginDatetime, String loginIp, Integer userId, Platform platform, Long timeout) {
        this.loginDatetime = loginDatetime;
        this.loginIp = loginIp;
        this.userId = userId;
        this.platform = platform;
        this.timeout = timeout;
    }

    /**
     * 登陆时间
     */
    private LocalDateTime loginDatetime;
    /**
     * 登录IP地址
     */
    private String loginIp;
    /**
     * 用户信息主键
     */
    private Integer userId;
    /**
     * 登录平台
     */
    private Platform platform;
    /**
     * 过期时间[分钟]
     */
    private Long timeout;

    public LocalDateTime getLoginDatetime() {
        return loginDatetime;
    }

    public void setLoginDatetime(LocalDateTime loginDatetime) {
        this.loginDatetime = loginDatetime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum Platform {
        /**
         * 浏览器
         */
        BROWSER,
        /**
         * 安卓
         */
        ANDROID,
        /**
         * IOS
         */
        IOS
    }
}
