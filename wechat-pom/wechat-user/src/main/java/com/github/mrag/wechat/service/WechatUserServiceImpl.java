package com.github.mrag.wechat.service;

import com.github.mrag.wechat.domain.WechatUser;
import com.github.mrag.wechat.mapper.WechatUserMapper;
import com.github.mrag.wechat.mapper.WechatUserRelationMapper;
import com.github.mrag.wechat.type.EnumRelationType;
import com.github.mrag.wechat.type.EnumStatus;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.mrag.wechat.mapper.WechatUserDynamicSqlSupport.wechatUser;
import static com.github.mrag.wechat.mapper.WechatUserRelationDynamicSqlSupport.wechatUserRelation;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Service
public class WechatUserServiceImpl implements WechatUserService {
    public WechatUserServiceImpl(WechatUserMapper wechatUserMapper, WechatUserRelationMapper wechatUserRelationMapper) {
        this.wechatUserMapper = wechatUserMapper;
        this.wechatUserRelationMapper = wechatUserRelationMapper;
    }

    private final WechatUserMapper wechatUserMapper;
    private final WechatUserRelationMapper wechatUserRelationMapper;

    @Override
    public WechatUser findByUserId(Integer wechatUserId) {
        return wechatUserMapper.selectByPrimaryKey(wechatUserId).orElse(null);
    }

    @Override
    public List<WechatUser> findRelationListByUserId(Integer wechatUserId) {
        SelectStatementProvider selectStatementProvider = select(WechatUserMapper.selectList)
                .from(wechatUser, "a")
                .join(wechatUserRelation, "b")
                .on(wechatUser.userId, equalTo(wechatUserRelation.fromUserId))
                .where(wechatUser.userId, isEqualTo(wechatUserId))
                .and(wechatUserRelation.relationType, isEqualTo(EnumRelationType.FRIEND))
                .and(wechatUserRelation.status, isEqualTo(EnumStatus.VALID))
                .build().render(RenderingStrategies.MYBATIS3);
        return wechatUserMapper.selectMany(selectStatementProvider);
    }
}
