package cn.gmwenterprise.presevere.vo;

/**
 * 登录成功后返回
 */
public class LoginSuccess {
    private String token;
    private Integer userId;

    public LoginSuccess() {
    }

    public LoginSuccess(String token, Integer userId) {
        this.token = token;
        this.userId = userId;
    }

    public LoginSuccess(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
