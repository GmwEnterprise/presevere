package cn.gmwenterprise.presevere.controller.client;

import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client/article")
public class ArticleClientController {
    @Resource
    ArticleService articleService;

    @GetMapping("/allTabs")
    public AjaxResult allTabs() {
        return AjaxResult.ok(articleService.getAllTabs());
    }
}
