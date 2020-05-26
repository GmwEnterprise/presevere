package com.github.mrag.wechat.controller;

import com.github.mrag.wechat.domain.WechatUser;
import com.github.mrag.wechat.service.WechatUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/wechatUser")
public class WechatUserController {
    @Resource
    private WechatUserService wechatUserService;

    @GetMapping("/{wechatUserId}")
    public WechatUser getOne(@PathVariable Integer wechatUserId) {
        return wechatUserService.getOne(wechatUserId);
    }
}
