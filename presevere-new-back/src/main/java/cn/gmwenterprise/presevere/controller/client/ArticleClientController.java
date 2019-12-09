package cn.gmwenterprise.presevere.controller.client;

import cn.gmwenterprise.presevere.domain.ArticleMetadata;
import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/client/article")
public class ArticleClientController {
    @Resource
    ArticleService articleService;

    @GetMapping("/allTabs")
    public AjaxResult allTabs() {
        return AjaxResult.ok(articleService.getAllTabs());
    }

    @GetMapping("/list")
    public AjaxResult getList(
        @RequestParam Integer start,
        @RequestParam(required = false, defaultValue = "publishedTime") String orderBy,
        @RequestParam(required = false, defaultValue = "true") Boolean desc) {
        PageHelper.startPage(start, 8);
        orderBy = orderBy.trim().length() > 0 ? orderBy : "publishedTime";
        List<ArticleMetadata> metadataList = articleService.getListOrderBy(orderBy, desc);
        return AjaxResult.ok(new PageInfo<>(metadataList));
    }
}
