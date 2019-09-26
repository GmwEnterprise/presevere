package cn.gmwenterprise.website.web.sys;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.vo.SysUserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthorityController implements BaseController {
    @Resource
    SysUserService sysUserServiceImpl;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysUserVo sysUserVo) {
        return sysUserServiceImpl.createUser(
            sysUserVo.getNickname(),
            sysUserVo.getUsername(),
            sysUserVo.getPassword()
        );
    }
}
