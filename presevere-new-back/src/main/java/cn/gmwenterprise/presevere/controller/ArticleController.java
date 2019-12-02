package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.RequirePermissions;
import cn.gmwenterprise.presevere.dto.ArticleDraftDto;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        return AjaxResult.ok(articleService.save(articleDraft));
    }

    @RequirePermissions(Permission.USER)
    @GetMapping("/drafts")
    public AjaxResult drafts(ArticleSearchDto condition) {
        Integer startPage = Optional.ofNullable(condition.getStartPage()).orElse(0);
        Integer count = Optional.ofNullable(condition.getCountByPage()).orElse(10);
        PageHelper.startPage(startPage, count);
        List<ArticleDraftMetaData> list = articleService.search(condition);
        return AjaxResult.ok(new PageInfo<>(list));
    }
}
