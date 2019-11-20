package cn.gmwenterprise.presevere.vo;

/**
 * 登录成功后返回
 */
public class LoginSuccess {
    private String token;

    public LoginSuccess() {
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
}
