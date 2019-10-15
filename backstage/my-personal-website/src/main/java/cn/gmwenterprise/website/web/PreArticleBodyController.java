package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.PreArticleBodyService;
import cn.gmwenterprise.website.vo.PreArticleBodyVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author gmw
 */
@RestController
@RequestMapping("/preArticleBody")
public class PreArticleBodyController implements BaseController {
    private final PreArticleBodyService preArticleBodyService;

    public PreArticleBodyController(PreArticleBodyService preArticleBodyService) {
        this.preArticleBodyService = preArticleBodyService;
    }

    @GetMapping("/{id}")
    public AjaxResult queryByPrimaryKey(@PathVariable Integer id) {
        return ok(preArticleBodyService.selectByPrimaryKey(id));
    }

    @GetMapping
    public AjaxResult queryPage(PreArticleBodyVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(preArticleBodyService.selectPage(vo)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public AjaxResult add(@RequestBody PreArticleBodyVo vo) {
        preArticleBodyService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public AjaxResult modify(@RequestBody PreArticleBodyVo vo) {
        preArticleBodyService.updateByPrimaryKey(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
        preArticleBodyService.deleteByPrimaryKey(id);
        return ok();
    }
}
