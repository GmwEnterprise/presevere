package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.AuthRequire;
import cn.gmwenterprise.presevere.common.AuthorizationHolder;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @AuthRequire("user")
    @GetMapping("/self")
    public AjaxResult getCurrentUserMessage() {
        return AjaxResult.ok(AuthorizationHolder.get().getCurrentUser());
    }
}
