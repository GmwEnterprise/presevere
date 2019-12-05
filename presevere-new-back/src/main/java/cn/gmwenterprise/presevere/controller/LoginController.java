package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.dto.SignDto;
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
        UsernameValidationResult result = loginService.verifyUsername(username, mode);
        return AjaxResult.ok(result);
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody SignDto body, HttpServletRequest request) {
        log.info("Login message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        LoginSuccess result = loginService.login(body, request);
        return AjaxResult.ok(result);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody SignDto body, HttpServletRequest request) {
        log.info("Register message: loginName[{}], password[{}]", body.getLoginName(), body.getPassword());
        LoginSuccess result = loginService.register(request, body);
        return AjaxResult.ok(result);
    }

    @PostMapping("/refreshToken/{userId}")
    public AjaxResult logout(@PathVariable Integer userId, HttpServletRequest request) {
        String token = loginService.refreshToken(userId, request.getRemoteHost());
        return AjaxResult.ok(token);
    }
}
