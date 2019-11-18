package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.dao.SysPermissionMapper;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> getAllPermissions(Integer userId) {
        return sysPermissionMapper.selectByUserId(userId);
    }
}
