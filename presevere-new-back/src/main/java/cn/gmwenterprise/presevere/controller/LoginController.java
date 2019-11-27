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

    @GetMapping("/verifyUsername/{mode}/{username}")
    public AjaxResult verifyUsername(@PathVariable String username, @PathVariable Integer mode) {
        return AjaxResult.ok(loginService.verifyUsername(username, mode));
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody DtoSign body, HttpServletRequest request) {
        log.info("Login message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        LoginSuccess result = loginService.login(body, request);
        return AjaxResult.ok(result);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody DtoSign body, HttpServletRequest request) {
        log.info("Register message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        LoginSuccess result = loginService.register(request, body);
        return AjaxResult.ok(result);
    }

    @GetMapping("/logout")
    @AuthRequire("user")
    public AjaxResult logout() {
        loginService.logout();
        return AjaxResult.ok(null);
    }
}
