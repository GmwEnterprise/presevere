package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.vo.LoginSuccess;
import cn.gmwenterprise.presevere.vo.UsernameValidationResult;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    LoginSuccess register(HttpServletRequest request, DtoSign body);

    LoginSuccess login(DtoSign body, HttpServletRequest request);

    /**
     * 根据给定的username创建一个独一无二的盐值
     *
     * @param username 用户名
     * @return salt, username, userId
     */
    SysUser randomSalt(String username);

    int LOGIN = 1;
    int REGISTRY = 2;

    /**
     * 验证用户名
     *
     * @param username 用户名
     * @return 验证结果封装
     */
    UsernameValidationResult verifyUsername(String username, int mode);

    String refreshToken(Integer userId, String host);
}
