package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.domain.SysRole;

import java.util.List;

public interface RoleService {

    void addRoles(List<SysRole> roles);

    /**
     * 批量添加用户角色
     *
     * @param userId 用户ID
     * @param roles  角色
     */
    void setRoles(Integer userId, String... roles);
}
