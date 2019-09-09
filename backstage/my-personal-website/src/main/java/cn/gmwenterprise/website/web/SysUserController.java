package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.vo.SysUserVo;
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

    @GetMapping("/{id}")
    public ResponseEntity queryByPrimaryKey(@PathVariable Integer id) {
        return ok(sysUserService.selectByPrimaryKey(id));
    }

    @GetMapping
    public ResponseEntity queryPage(SysUserVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(sysUserService.selectPage(vo)));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody SysUserVo vo) {
        sysUserService.insert(vo);
        return ok();
    }

    @PatchMapping
    public ResponseEntity modify(@RequestBody SysUserVo vo) {
        sysUserService.updateByPrimaryKey(vo);
        return ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        sysUserService.deleteByPrimaryKey(id);
        return ok();
    }
}
