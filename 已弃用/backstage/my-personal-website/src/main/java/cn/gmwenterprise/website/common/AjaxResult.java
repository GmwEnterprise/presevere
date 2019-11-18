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
     * 权限不足，拒绝访问
     */
    public static final int CODE_ACCESS_DENY = 2;
    /**
     * 需要登录验证权限
     */
    public static final int CODE_NEED_AUTH = 3;
    /**
     * 登录失败
     */
    public static final int CODE_LOGIN_FAILURE = 4;

    public static AjaxResult ok(String msg, Object data) {
        return of(CODE_SUCCESS, msg, data);
    }

    public static AjaxResult ok(Object data) {
        return of(CODE_SUCCESS, "success", data);
    }

    public static AjaxResult ok() {
        return of(CODE_SUCCESS, "success");
    }

    public static AjaxResult fail(String msg) {
        return of(CODE_ERROR, msg);
    }

    public static AjaxResult fail(int code, String msg) {
        return of(code, msg);
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
