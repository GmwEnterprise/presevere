package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.vo.SysUserVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController implements BaseController {
    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity queryByPrimaryKey(@PathVariable Integer id) {
        return ok(sysUserService.selectByPrimaryKey(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity queryPage(SysUserVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(sysUserService.selectPage(vo)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity add(@RequestBody SysUserVo vo) {
        sysUserService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ResponseEntity modify(@RequestBody SysUserVo vo) {
        sysUserService.updateByPrimaryKey(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        sysUserService.deleteByPrimaryKey(id);
        return ok();
    }
}
