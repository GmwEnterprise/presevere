package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.dao.SysRoleMapper;
import cn.gmwenterprise.presevere.dao.SysUserRoleMapper;
import cn.gmwenterprise.presevere.domain.SysRole;
import cn.gmwenterprise.presevere.domain.SysUserRole;
import cn.gmwenterprise.presevere.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    SysRoleMapper sysRoleMapper;
    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void addRoles(List<SysRole> roles) {
        roles.forEach(role -> {
            SysRole exist = sysRoleMapper.selectByRole(role.getRole());
            if (exist == null) {
                sysRoleMapper.insertSelective(role);
                log.info("Role[{}] is inserted.", role);
            } else {
                log.info("ROLE[{}] is already exists: {}", exist.getRole(), exist);
            }
        });
    }

    @Override
    public void setRoles(Integer userId, String... roles) {
        Arrays.stream(roles)
            .map(sysRoleMapper::selectByRole)
            .filter(Objects::nonNull)
            .map(role -> new SysUserRole(userId, role.getId()))
            .forEach(sysUserRoleMapper::insertSelective);
    }
}
