package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.domain.SysUser;

import java.util.List;

public interface UserService {

    List<SysPermission> getUserPermissionsByUserId(Integer userId);

    SysUser getUserById(Integer userId);
}
