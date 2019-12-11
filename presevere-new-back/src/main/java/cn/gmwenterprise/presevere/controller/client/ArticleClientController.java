package cn.gmwenterprise.presevere.controller.client;

import cn.gmwenterprise.presevere.domain.ArticleMetadata;
import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import cn.gmwenterprise.presevere.vo.Archive;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/client/article")
public class ArticleClientController {
    @Resource
    ArticleService articleService;

    @GetMapping("/post/{urlNumber}")
    public AjaxResult post(@PathVariable Long urlNumber) {
        return AjaxResult.ok(articleService.getArticleByUrl(urlNumber));
    }

    @GetMapping("/allTabs")
    public AjaxResult allTabs() {
        return AjaxResult.ok(articleService.getAllTabs());
    }

    @GetMapping("/list")
    public AjaxResult getList(
        @RequestParam Integer start,
        @RequestParam(required = false, defaultValue = "publishedTime") String orderBy,
        @RequestParam(required = false, defaultValue = "true") Boolean desc,
        @RequestParam(required = false, defaultValue = "") String tag) {
        PageHelper.startPage(start, 8);
        orderBy = orderBy.trim().length() > 0 ? orderBy : "publishedTime";
        List<ArticleMetadata> metadataList = articleService.getListOrderBy(orderBy, desc, tag);
        return AjaxResult.ok(new PageInfo<>(metadataList));
    }

    @GetMapping("/archive/{year}")
    public AjaxResult archive(@PathVariable Integer year) {
        Archive archive = articleService.getArchiveDataByYear(year);
        return AjaxResult.ok(archive);
    }
}
