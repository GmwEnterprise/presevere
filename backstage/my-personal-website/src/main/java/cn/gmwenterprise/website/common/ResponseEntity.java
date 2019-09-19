package cn.gmwenterprise.website.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ResponseEntity {
    /**
     * 状态码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

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

    public static ResponseEntity of(int code, String msg, Object data) {
        return new ResponseEntity(code, msg, data);
    }

    public static ResponseEntity of(int code, String msg) {
        return new ResponseEntity(code, msg, null);
    }
}
