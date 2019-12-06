package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.config.security.Authorization;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.domain.SysUser;

import java.util.List;

public interface UserService {

    List<SysPermission> getUserPermissionsByUserId(Integer userId);

    SysUser getUserById(Integer userId);

    boolean userIdExists(Integer userId);

    boolean doesNicknameUsed(Integer userId, String nickname);

    boolean doesPhoneUsed(Integer userId, String phone);

    boolean doesEmailUsed(Integer userId, String email);

    void modifyUser(SysUser user, Authorization authorization);
}
