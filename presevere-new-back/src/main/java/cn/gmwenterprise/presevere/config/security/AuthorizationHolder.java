package cn.gmwenterprise.presevere.config.security;

public final class AuthorizationHolder {
    private static final ThreadLocal<Authorization> AUTHORIZATION = new ThreadLocal<>();

    public static void set(Authorization authorization) {
        if (AUTHORIZATION.get() == null) {
            AUTHORIZATION.set(authorization);
        }
    }

    public static Authorization get() {
        return AUTHORIZATION.get();
    }

    public static boolean exist() {
        return AUTHORIZATION.get() != null;
    }

    public static void remove() {
        AUTHORIZATION.remove();
    }
}
