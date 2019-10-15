package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.PreArticleDraftService;
import cn.gmwenterprise.website.vo.PreArticleDraftVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/preArticleDraft")
public class PreArticleDraftController implements BaseController {
    private final PreArticleDraftService preArticleDraftService;

    public PreArticleDraftController(PreArticleDraftService preArticleDraftService) {
        this.preArticleDraftService = preArticleDraftService;
    }

    @GetMapping("/{id}")
    public AjaxResult queryByPrimaryKey(@PathVariable Integer id) {
        return ok(preArticleDraftService.selectByPrimaryKey(id));
    }

    @GetMapping
    public AjaxResult queryPage(PreArticleDraftVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(preArticleDraftService.selectPage(vo)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public AjaxResult add(@RequestBody PreArticleDraftVo vo) {
        preArticleDraftService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public AjaxResult modify(@RequestBody PreArticleDraftVo vo) {
        preArticleDraftService.updateByPrimaryKey(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
        preArticleDraftService.deleteByPrimaryKey(id);
        return ok();
    }
}
