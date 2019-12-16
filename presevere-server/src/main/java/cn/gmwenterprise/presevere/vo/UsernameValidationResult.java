package cn.gmwenterprise.presevere.vo;

public class UsernameValidationResult {
    private Boolean value;

    public UsernameValidationResult() {}

    public static UsernameValidationResult valid() {
        UsernameValidationResult result = new UsernameValidationResult();
        result.setValue(true);
        return result;
    }

    public static UsernameValidationResult invalid() {
        UsernameValidationResult result = new UsernameValidationResult();
        result.setValue(false);
        return result;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
