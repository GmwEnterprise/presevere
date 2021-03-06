package cn.gmwenterprise.presevere.domain;

public class SysRole {
    public SysRole() {
    }

    public SysRole(String role, String roleName) {
        this.role = role;
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return String.format("SysRole{id=%d, roleName='%s', role='%s'}", id, roleName, role);
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.role_name
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String roleName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.role
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String role;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.id
     *
     * @return the value of sys_role.id
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.id
     *
     * @param id the value for sys_role.id
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.role_name
     *
     * @return the value of sys_role.role_name
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.role_name
     *
     * @param roleName the value for sys_role.role_name
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.role
     *
     * @return the value of sys_role.role
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.role
     *
     * @param role the value for sys_role.role
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setRole(String role) {
        this.role = role;
    }
}