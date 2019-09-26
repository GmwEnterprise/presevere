package cn.gmwenterprise.website.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class AjaxResult {
    /**
     * 成功
     */
    public static final int CODE_SUCCESS = 0;
    /**
     * 接口处理失败
     */
    public static final int CODE_ERROR = -1;
    /**
     * 拒绝访问
     */
    public static final int CODE_ACCESS_DENY = 100;
    /**
     * 登录失败
     */
    public static final int CODE_LOGIN_FAILURE = 101;

    public static AjaxResult ok(String message, Object data) {
        return of(CODE_SUCCESS, message, data);
    }

    public static AjaxResult ok(Object data) {
        return of(CODE_SUCCESS, "success", data);
    }

    public static AjaxResult ok() {
        return of(CODE_SUCCESS, "success");
    }

    public static AjaxResult fail(String message) {
        return of(CODE_ERROR, message);
    }

    public static AjaxResult fail(int code, String message) {
        return of(code, message);
    }

    public static AjaxResult of(int code, String msg, Object data) {
        return new AjaxResult(code, msg, data);
    }

    public static AjaxResult of(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    private int code;
    private String msg;
    private Object data;
}
