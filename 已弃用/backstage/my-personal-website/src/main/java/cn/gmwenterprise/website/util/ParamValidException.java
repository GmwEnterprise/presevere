package cn.gmwenterprise.website.util;

/**
 * 验证参数异常
 */
public class ParamValidException extends Exception {
    public ParamValidException() {
        super();
    }

    public ParamValidException(String message) {
        super(message);
    }

    public ParamValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamValidException(Throwable cause) {
        super(cause);
    }

    protected ParamValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
