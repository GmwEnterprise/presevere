package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.service.PreArticleMsgService;
import cn.gmwenterprise.website.vo.PreArticleMsgVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 文章基本信息控制接口
 *
 * @author gmw
 */
@RestController
@RequestMapping("/preArticleMsg")
public class PreArticleMsgController implements BaseController {
    private final PreArticleMsgService preArticleMsgService;

    public PreArticleMsgController(PreArticleMsgService preArticleMsgService) {
        this.preArticleMsgService = preArticleMsgService;
    }

    @GetMapping("/{id}")
    public AjaxResult queryByPrimaryKey(@PathVariable Integer id) {
        return ok(preArticleMsgService.selectByPrimaryKey(id));
    }

    @GetMapping("/detail/{id}")
    public AjaxResult queryArticleById(@PathVariable Integer id) {
        return ok(preArticleMsgService.getArticleById(id));
    }

    @GetMapping
    public AjaxResult queryPage(PreArticleMsgVo vo) {
        PageHelper.startPage(vo.getCurrentPage(), vo.getPageSize());
        return ok(PageHelper.page(preArticleMsgService.selectPage(vo)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public AjaxResult add(@RequestBody PreArticleMsgVo vo) {
        preArticleMsgService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public AjaxResult modify(@RequestBody PreArticleMsgVo vo) {
        preArticleMsgService.updateByPrimaryKey(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
        preArticleMsgService.deleteByPrimaryKey(id);
        return ok();
    }
}
