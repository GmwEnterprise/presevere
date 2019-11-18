package cn.gmwenterprise.presevere.config;

import cn.gmwenterprise.presevere.common.Constants;
import cn.gmwenterprise.presevere.service.RoleService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class SystemConfig {
    @PostConstruct
    public void init() {
        // 初始化系统基本角色
        initBasicRoleData();
    }

    @Resource
    RoleService roleService;

    /**
     * 初始化系统基本角色
     */
    private void initBasicRoleData() {
        roleService.addRoles(Constants.BASIC_ROLE_LIST);
    }
}
