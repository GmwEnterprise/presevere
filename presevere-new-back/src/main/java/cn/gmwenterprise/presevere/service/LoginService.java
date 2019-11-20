package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.vo.LoginSuccess;

public interface LoginService {

    LoginSuccess register(String username, String password);

    String login(DtoSign body);

    void logout();

    /**
     * 根据给定的username创建一个独一无二的盐值
     *
     * @param username 用户名
     * @return salt, username, userId
     */
    SysUser randomSalt(String username);
}
