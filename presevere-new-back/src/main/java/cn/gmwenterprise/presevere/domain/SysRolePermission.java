package cn.gmwenterprise.presevere.domain;

public class SysRolePermission {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_permission.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_permission.role_id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_permission.permission_id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer permissionId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_permission.id
     *
     * @return the value of sys_role_permission.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_permission.id
     *
     * @param id the value for sys_role_permission.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_permission.role_id
     *
     * @return the value of sys_role_permission.role_id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_permission.role_id
     *
     * @param roleId the value for sys_role_permission.role_id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_permission.permission_id
     *
     * @return the value of sys_role_permission.permission_id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_permission.permission_id
     *
     * @param permissionId the value for sys_role_permission.permission_id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}