package com.github.mrag.wechat.service;

import com.github.mrag.wechat.domain.WechatUser;

import java.util.List;

public interface WechatUserService {
    WechatUser findByUserId(Integer wechatUserId);

    List<WechatUser> findRelationListByUserId(Integer userId);
}
