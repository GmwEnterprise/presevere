package cn.gmwenterprise.presevere.vo;

public class UsernameValidationResult {
    private boolean frontEncoded;
    private String salt;

    public UsernameValidationResult(boolean frontEncoded, String salt) {
        this.frontEncoded = frontEncoded;
        this.salt = salt;
    }

    public UsernameValidationResult() {
    }

    public boolean isFrontEncoded() {
        return frontEncoded;
    }

    public void setFrontEncoded(boolean frontEncoded) {
        this.frontEncoded = frontEncoded;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
