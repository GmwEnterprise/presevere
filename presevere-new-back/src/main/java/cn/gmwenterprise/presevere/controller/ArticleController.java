package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.Authorization;
import cn.gmwenterprise.presevere.config.security.AuthorizationHolder;
import cn.gmwenterprise.presevere.config.security.RequirePermissions;
import cn.gmwenterprise.presevere.domain.ArticleDraftWithBLOBs;
import cn.gmwenterprise.presevere.dto.ArticleDraftDto;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;
import cn.gmwenterprise.presevere.vo.Res;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;

    @RequirePermissions(Permission.USER)
    @PostMapping("/save")
    public AjaxResult save(@RequestBody ArticleDraftDto articleDraft) {
        return AjaxResult.ok(articleService.save(articleDraft, AuthorizationHolder.get()));
    }

    @RequirePermissions(Permission.USER)
    @GetMapping("/drafts")
    public AjaxResult drafts(ArticleSearchDto condition) {
        Integer startPage = Optional.ofNullable(condition.getStartPage()).orElse(1);
        Integer count = Optional.ofNullable(condition.getCountByPage()).orElse(10);
        System.out.println("startPage = " + startPage + ", count = " + count);
        PageHelper.startPage(startPage, count);
        List<ArticleDraftMetaData> list = articleService.search(condition, AuthorizationHolder.get());
        return AjaxResult.ok(new PageInfo<>(list));
    }

    @RequirePermissions(Permission.USER)
    @GetMapping("/draft/{draftId}")
    public AjaxResult draft(@PathVariable Integer draftId) {
        ArticleDraftWithBLOBs data = articleService.get(draftId);
        if (!AuthorizationHolder.get().getTokenPayload().getUserId().equals(data.getWriter())) {
            // 这个接口只能是自己访问自己的
            return AjaxResult.res(Res.UNAUTHORIZED, "您不具有访问该资源的权限");
        }
        return AjaxResult.ok(data);
    }

    @RequirePermissions(Permission.USER)
    @DeleteMapping("/draft/{draftId}")
    public AjaxResult delete(@PathVariable Integer draftId) {
        articleService.deleteDraft(draftId);
        return AjaxResult.ok(null);
    }

    @RequirePermissions(Permission.USER)
    @PostMapping("/publish")
    public AjaxResult publish(String key) {
        articleService.publish(key);
        return AjaxResult.ok(null);
    }
}
