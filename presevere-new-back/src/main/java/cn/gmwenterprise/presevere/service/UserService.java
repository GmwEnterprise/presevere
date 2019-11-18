package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.domain.SysPermission;

import java.util.List;

public interface UserService {

    List<SysPermission> getAllPermissions(Integer userId);
}
