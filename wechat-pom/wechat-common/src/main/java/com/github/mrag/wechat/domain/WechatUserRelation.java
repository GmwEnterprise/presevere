package com.github.mrag.wechat.domain;

import com.github.mrag.wechat.type.EnumRelationType;
import com.github.mrag.wechat.type.EnumStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.annotation.Generated;

public class WechatUserRelation implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer fromUserId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer toUserId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private EnumRelationType relationType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime createdTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime establishedTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private EnumStatus status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private static final long serialVersionUID = 1L;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getFromUserId() {
        return fromUserId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getToUserId() {
        return toUserId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public EnumRelationType getRelationType() {
        return relationType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRelationType(EnumRelationType relationType) {
        this.relationType = relationType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getEstablishedTime() {
        return establishedTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEstablishedTime(LocalDateTime establishedTime) {
        this.establishedTime = establishedTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public EnumStatus getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(EnumStatus status) {
        this.status = status;
    }
}