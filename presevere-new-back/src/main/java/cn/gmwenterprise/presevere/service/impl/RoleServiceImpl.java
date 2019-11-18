package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.dao.SysRoleMapper;
import cn.gmwenterprise.presevere.domain.SysRole;
import cn.gmwenterprise.presevere.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    SysRoleMapper sysRoleMapper;

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
}
