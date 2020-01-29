package cn.gmwenterprise.website.web;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.service.PreArticleMsgService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client")
public class ClientRequestController {
    @Resource
    PreArticleMsgService preArticleMsgService;

    @GetMapping("/tags")
    public AjaxResult queryTags() {
        preArticleMsgService.queryAllTags();
        return null;
    }
}
