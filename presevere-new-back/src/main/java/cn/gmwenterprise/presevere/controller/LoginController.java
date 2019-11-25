package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.config.security.AuthRequire;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import cn.gmwenterprise.presevere.vo.LoginSuccess;
import cn.gmwenterprise.presevere.vo.UsernameValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sign")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    LoginService loginService;

    @GetMapping("/validUsername/{username}")
    public AjaxResult validUsername(@PathVariable String username) {
        UsernameValidationResult validationResult = loginService.validUsername(username);
        return AjaxResult.ok(validationResult);
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody DtoSign body, HttpServletRequest request) {
        log.info("Login message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        LoginSuccess result = loginService.login(body, request);
        return AjaxResult.ok(result);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody DtoSign body, HttpServletRequest request) {
        // body.loginName: 已保存在库中的用户名username
        // body.password: 前端加盐加密后的密码
        log.info("Register message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        LoginSuccess result = loginService.register(request, body);
        return AjaxResult.ok(result);
    }

    @GetMapping("/randomSalt/{username}")
    public AjaxResult randomSalt(@PathVariable String username) {
        SysUser saltUser = loginService.randomSalt(username);
        return AjaxResult.ok(saltUser);
    }

    @GetMapping("/logout")
    @AuthRequire("user")
    public AjaxResult logout() {
        loginService.logout();
        return AjaxResult.ok(null);
    }
}
