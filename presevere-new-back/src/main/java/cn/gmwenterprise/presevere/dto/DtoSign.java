package cn.gmwenterprise.presevere.dto;

public class DtoSign {
    private String loginName;
    private String password;
    private Boolean keepLogin;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getKeepLogin() {
        return keepLogin;
    }

    public void setKeepLogin(Boolean keepLogin) {
        this.keepLogin = keepLogin;
    }
}
