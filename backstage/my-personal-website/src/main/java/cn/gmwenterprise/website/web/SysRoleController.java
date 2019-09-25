package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.SysRoleService;
import cn.gmwenterprise.website.vo.SysRoleVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController implements BaseController {
    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity queryByPrimaryKey(@PathVariable Integer id) {
        return ok(sysRoleService.selectByPrimaryKey(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity queryPage(SysRoleVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(sysRoleService.selectPage(vo)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity add(@RequestBody SysRoleVo vo) {
        sysRoleService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping
    public ResponseEntity modify(@RequestBody SysRoleVo vo) {
        sysRoleService.updateByPrimaryKey(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        sysRoleService.deleteByPrimaryKey(id);
        return ok();
    }
}
