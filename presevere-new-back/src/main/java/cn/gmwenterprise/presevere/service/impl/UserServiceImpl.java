package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.dao.SysPermissionMapper;
import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    SysPermissionMapper sysPermissionMapper;
    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public List<SysPermission> getUserPermissionsByUserId(Integer userId) {
        return sysPermissionMapper.selectByUserId(userId);
    }

    @Override
    public SysUser getUserById(Integer userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean userIdExists(Integer userId) {
        return sysUserMapper.countByUserId(userId) == 1;
    }
}
