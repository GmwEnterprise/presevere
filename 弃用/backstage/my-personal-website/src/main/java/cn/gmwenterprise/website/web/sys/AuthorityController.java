package cn.gmwenterprise.website.web.sys;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.util.ParamValidException;
import cn.gmwenterprise.website.util.ParamValidator;
import cn.gmwenterprise.website.vo.SysUserVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthorityController implements BaseController {
    @Resource
    SysUserService sysUserServiceImpl;

    @GetMapping("/welcome")
    public String welcomeEveryone() {
        return "Welcome everyone!";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/welcomeUser")
    public String welcomeUser() {
        return "Welcome user!";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/welcomeAdmin")
    public String welcomeAdmin() {
        return "Welcome admin!";
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysUserVo sysUserVo) {
        // 验证参数
        try {
            ParamValidator.validNickname(sysUserVo.getNickname());
            ParamValidator.validUsername(sysUserVo.getUsername());
            ParamValidator.validPassword(sysUserVo.getPassword());
        } catch (ParamValidException e) {
            return fail(e.getMessage());
        }
        return sysUserServiceImpl.createUser(
            sysUserVo.getNickname(),
            sysUserVo.getUsername(),
            sysUserVo.getPassword()
        );
    }
}
