package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.SysUserRoleService;
import cn.gmwenterprise.website.vo.SysUserRoleVo;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleController implements BaseController {
    private final SysUserRoleService sysUserRoleService;

    public SysUserRoleController(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity queryByPrimaryKey(@PathVariable Integer id) {
        return ok(sysUserRoleService.selectByPrimaryKey(id));
    }

    @GetMapping
    public ResponseEntity queryPage(SysUserRoleVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(sysUserRoleService.selectPage(vo)));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody SysUserRoleVo vo) {
        sysUserRoleService.insert(vo);
        return ok();
    }

    @PatchMapping
    public ResponseEntity modify(@RequestBody SysUserRoleVo vo) {
        sysUserRoleService.updateByPrimaryKey(vo);
        return ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        sysUserRoleService.deleteByPrimaryKey(id);
        return ok();
    }
}
