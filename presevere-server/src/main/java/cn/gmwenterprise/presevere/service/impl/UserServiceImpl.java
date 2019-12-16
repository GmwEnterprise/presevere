package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.BusinessException;
import cn.gmwenterprise.presevere.config.security.Authorization;
import cn.gmwenterprise.presevere.dao.SysPermissionMapper;
import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean doesNicknameUsed(Integer userId, String nickname) {
        return sysUserMapper.countByFullNickname(userId, nickname) > 0;
    }

    @Override
    public boolean doesPhoneUsed(Integer userId, String phone) {
        return sysUserMapper.countByPhone(userId, phone) > 0;
    }

    @Override
    public boolean doesEmailUsed(Integer userId, String email) {
        return sysUserMapper.countByEmail(userId, email) > 0;
    }

    @Override
    public void modifyUser(SysUser user, Authorization authorization) {
        if (user == null || user.getId() == null || user.getNickname() == null) {
            throw new BusinessException("非法请求");
        }
        if (authorization.getTokenPayload().getUserId().equals(user.getId())) {
            SysUser tobeModify = new SysUser();
            tobeModify.setId(user.getId());
            tobeModify.setNickname(user.getNickname());
            tobeModify.setPhone(Optional.ofNullable(user.getPhone()).orElse(""));
            tobeModify.setEmail(Optional.ofNullable(user.getEmail()).orElse(""));
            sysUserMapper.updateByPrimaryKeySelective(tobeModify);
        } else {
            throw new BusinessException("您不具有访问该资源的权限!");
        }
    }
}
