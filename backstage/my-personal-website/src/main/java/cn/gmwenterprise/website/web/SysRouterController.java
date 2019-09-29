package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.SysRouterService;
import cn.gmwenterprise.website.vo.SysRouterVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/sysRouter")
public class SysRouterController implements BaseController {
    private final SysRouterService sysRouterService;

    public SysRouterController(SysRouterService sysRouterService) {
        this.sysRouterService = sysRouterService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public AjaxResult queryByPrimaryKey(@PathVariable Integer id) {
        return ok(sysRouterService.selectByPrimaryKey(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public AjaxResult queryPage(SysRouterVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(sysRouterService.selectPage(vo)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public AjaxResult add(@RequestBody SysRouterVo vo) {
        sysRouterService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping
    public AjaxResult modify(@RequestBody SysRouterVo vo) {
        sysRouterService.updateByPrimaryKey(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
        sysRouterService.deleteByPrimaryKey(id);
        return ok();
    }
}
