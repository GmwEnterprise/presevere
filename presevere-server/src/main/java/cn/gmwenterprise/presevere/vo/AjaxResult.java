package cn.gmwenterprise.presevere.vo;

public final class AjaxResult {
    public static AjaxResult ok(Object data) {
        return new AjaxResult(Res.OK.getCode(), Res.OK.getMsg(), data);
    }

    public static AjaxResult error(Object data) {
        return new AjaxResult(Res.ERROR.getCode(), Res.ERROR.getMsg(), data);
    }

    public static AjaxResult res(Res status) {
        return new AjaxResult(status.getCode(), status.getMsg(), null);
    }

    public static AjaxResult res(Res status, Object data) {
        return new AjaxResult(status.getCode(), status.getMsg(), data);
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
