package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.config.mybatis.PageHelper;
import cn.gmwenterprise.website.config.security.User;
import cn.gmwenterprise.website.domain.PreArticleDraft;
import cn.gmwenterprise.website.service.PreArticleDraftService;
import cn.gmwenterprise.website.vo.PreArticleDraftVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        vo.setCreator(currentUser.getUser().getId());
        preArticleDraftService.insert(vo);
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addAndReturn")
    public AjaxResult addAndReturn(@RequestBody PreArticleDraftVo vo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        vo.setCreator(currentUser.getUser().getId());
        preArticleDraftService.insert(vo);
        return ok(vo);
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/pushTag")
    public AjaxResult pushTag(@RequestBody PreArticleDraftVo vo) {
        preArticleDraftService.pushTag(vo.getId(), vo.getTag());
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/removeTagById")
    public AjaxResult removeTag(@RequestBody PreArticleDraftVo vo) {
        preArticleDraftService.removeTagById(vo.getId(), vo.getTag());
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/pushContent")
    public AjaxResult pushContent(@RequestBody PreArticleDraft vo) {
        if (vo.getId() != null) {
            preArticleDraftService.pushContentById(vo.getId(), vo.getContent());
            return ok();
        } else {
            Integer primary = preArticleDraftService.pushContent(vo.getContent());
            return ok(new PreArticleDraftVo() {{
                setId(primary);
            }});
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/updateTitle")
    public AjaxResult updateTitle(@RequestBody PreArticleDraft vo) {
        preArticleDraftService.updateTitle(vo.getId(), vo.getTitle());
        return ok();
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/updateIntroduction")
    public AjaxResult updateIntroduction(@RequestBody PreArticleDraft vo) {
        preArticleDraftService.updateIntroduction(vo.getId(), vo.getIntroduction());
        return ok();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody PreArticleDraftVo vo) {
        preArticleDraftService.publishArticle(vo.getId());
        return ok();
    }
}
