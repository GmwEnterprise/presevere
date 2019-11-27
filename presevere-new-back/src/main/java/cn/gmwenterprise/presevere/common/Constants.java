package cn.gmwenterprise.presevere.common;

public interface Constants {
    /**
     * 默认编码
     */
    String UTF_8 = "UTF-8";
    /**
     * 默认ContentType
     */
    String APPLICATION_JSON = "application/json";
    /**
     * 默认过期时间，单位：分钟
     */
    Long DEFAULT_TOKEN_TIMEOUT = 40L;
    /**
     * 默认token无痛刷新时间，单位：分钟
     */
    Long DEFAULT_REFRESH_TOKEN_TIMEOUT = 3L;

    String TOKEN_REFRESH_KEY = "user_token_refresh_key";
    Long TIMEOUT_ON = 1L;
    Long TIMEOUT_OFF = 0L;
}
