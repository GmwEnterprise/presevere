package cn.gmwenterprise.presevere.domain;

public class SysPermission {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.permission
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String permission;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.id
     *
     * @return the value of sys_permission.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.id
     *
     * @param id the value for sys_permission.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.permission
     *
     * @return the value of sys_permission.permission
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getPermission() {
        return permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.permission
     *
     * @param permission the value for sys_permission.permission
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }
}