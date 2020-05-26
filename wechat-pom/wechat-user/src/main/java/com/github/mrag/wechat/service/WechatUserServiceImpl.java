package com.github.mrag.wechat.service;

import com.github.mrag.wechat.domain.WechatUser;
import com.github.mrag.wechat.mapper.WechatUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WechatUserServiceImpl implements WechatUserService {
    @Resource
    WechatUserMapper wechatUserMapper;
    @Override
    public WechatUser getOne(Integer wechatUserId) {
        return wechatUserMapper.selectByPrimaryKey(wechatUserId).orElse(null);
    }
}
