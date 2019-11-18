package cn.gmwenterprise.presevere.vo;

public class AjaxResult {
    private static final int CODE_SUCCESS = 200;
    private static final String MESSAGE_SUCCESS = "request success";
    private static final int CODE_ERROR = 500;
    private static final String MESSAGE_ERROR = "request error";

    public static AjaxResult ok(Object data) {
        return new AjaxResult(CODE_SUCCESS, MESSAGE_SUCCESS, data);
    }

    public static AjaxResult fail(Object data) {
        return new AjaxResult(CODE_ERROR, MESSAGE_ERROR, data);
    }

    private int code;
    private String message;
    private Object data;

    private AjaxResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
