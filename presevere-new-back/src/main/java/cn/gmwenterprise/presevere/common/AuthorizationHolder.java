package cn.gmwenterprise.presevere.common;

import cn.gmwenterprise.presevere.domain.SysUser;

public final class AuthorizationHolder {
    private static ThreadLocal<SysUser> currentUser = new ThreadLocal<>();

    public static SysUser getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(SysUser sysUser) {
        currentUser.set(sysUser);
    }

    public static void removeCurrentUser() {
        if (currentUser.get() != null) {
            currentUser.remove();
        }
    }
}
