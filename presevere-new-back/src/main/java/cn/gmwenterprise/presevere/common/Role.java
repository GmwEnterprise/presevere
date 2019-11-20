package cn.gmwenterprise.presevere.common;

public enum Role {
    /**
     * 用户角色
     */
    USER("ROLE_USER"),
    /**
     * 管理员角色
     */
    ADMIN("ROLE_ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
