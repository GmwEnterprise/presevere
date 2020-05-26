package com.github.mrag.wechat.service;

import com.github.mrag.wechat.domain.WechatUser;

public interface WechatUserService {
    WechatUser getOne(Integer wechatUserId);
}
