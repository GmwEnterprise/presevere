package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.RuquirePermissions;
import cn.gmwenterprise.presevere.dto.ArticleDraftDto;
import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;

    @RuquirePermissions(Permission.USER)
    @PostMapping("/save")
    public AjaxResult saveDraft(@RequestBody ArticleDraftDto articleDraft) {
        return AjaxResult.ok(articleService.save(articleDraft));
    }
}
