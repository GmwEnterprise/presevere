package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.AuthRequire;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sign")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    LoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody DtoSign body) {
        log.info("Login message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        return AjaxResult.ok(body);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody DtoSign body) {
        log.info("Register message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        loginService.register(body);
        return AjaxResult.ok(null);
    }

    @GetMapping("/logout")
    @AuthRequire("view")
    public AjaxResult logout() {
        loginService.logout();
        return AjaxResult.ok(null);
    }
}
