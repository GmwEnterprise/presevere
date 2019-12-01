package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.Auth;
import cn.gmwenterprise.presevere.config.security.AuthRequire;
import cn.gmwenterprise.presevere.config.security.AuthorizationHolder;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @AuthRequire(Auth.USER)
    @GetMapping("/currentUser")
    public AjaxResult getCurrentUserMessage() {
        return AjaxResult.ok(AuthorizationHolder.get().currentUser());
    }
}
