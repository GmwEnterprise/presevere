package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.AuthorizationHolder;
import cn.gmwenterprise.presevere.config.security.RequirePermissions;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.service.UserService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @RequirePermissions(Permission.USER)
    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody SysUser user) {
        userService.modifyUser(user, AuthorizationHolder.get());
        return AjaxResult.ok(null);
    }

    @RequirePermissions(Permission.USER)
    @GetMapping("/currentUser")
    public AjaxResult getCurrentUserMessage() {
        return AjaxResult.ok(AuthorizationHolder.get().currentUser());
    }

    @GetMapping("/nicknameUsed")
    public AjaxResult nicknameUsed(Integer userId, String nickname) {
        return AjaxResult.ok(userService.doesNicknameUsed(userId, nickname));
    }

    @GetMapping("/phoneUsed")
    public AjaxResult phoneUsed(Integer userId, String phone) {
        return AjaxResult.ok(userService.doesPhoneUsed(userId, phone));
    }

    @GetMapping("/emailUsed")
    public AjaxResult emailUsed(Integer userId, String email) {
        return AjaxResult.ok(userService.doesEmailUsed(userId, email));
    }
}
