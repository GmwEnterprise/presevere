package cn.gmwenterprise.presevere.controller;

import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.RuquirePermissions;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/t")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    @RuquirePermissions(Permission.USER)
    public AjaxResult helloWorld() {
        return AjaxResult.ok("Hello world !");
    }

    @GetMapping("/hey")
    public AjaxResult hey() {
        return AjaxResult.ok("Hey !");
    }

    @GetMapping("/date")
    public AjaxResult test(LocalDateTime dateTime) {
        log.info(dateTime.toString());
        return AjaxResult.ok(LocalDateTime.now());
    }

    @GetMapping("/date2")
    public AjaxResult test2(SysUser data) {
        log.info(data.getCreateTime().toString());
        return AjaxResult.ok(new SysUser() {{
            setCreateTime(LocalDateTime.now());
        }});
    }

    @PostMapping("/date3")
    public AjaxResult test3(@RequestBody SysUser data) {
        log.info(data.getCreateTime().toString());
        return AjaxResult.ok(new SysUser() {{
            setCreateTime(LocalDateTime.now());
        }});
    }
}
