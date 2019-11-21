package cn.gmwenterprise.presevere.vo;

public enum Res {

    /**
     * [1] 请求成功
     */
    OK(1, "请求成功"),

    /**
     * [2] 请求失败
     */
    ERROR(2, "请求失败"),

    /**
     * [3] 需要登录
     */
    LOGIN_REQUIRED(3, "需要登录"),

    /**
     * [4] 权限不足
     */
    UNAUTHORIZED(4, "权限不足");

    private int code;
    private String msg;

    Res(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
