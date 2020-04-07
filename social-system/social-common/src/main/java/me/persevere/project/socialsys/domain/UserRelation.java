package me.persevere.project.socialsys.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * user_relation
 * @author 
 */
public class UserRelation implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 自己
     */
    private Long selfUser;

    /**
     * 对方
     */
    private Long anotherUser;

    /**
     * 关系类型 1-关注，2-拉黑
     */
    private Byte relationType;

    /**
     * 操作时间
     */
    private LocalDateTime operatingTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSelfUser() {
        return selfUser;
    }

    public void setSelfUser(Long selfUser) {
        this.selfUser = selfUser;
    }

    public Long getAnotherUser() {
        return anotherUser;
    }

    public void setAnotherUser(Long anotherUser) {
        this.anotherUser = anotherUser;
    }

    public Byte getRelationType() {
        return relationType;
    }

    public void setRelationType(Byte relationType) {
        this.relationType = relationType;
    }

    public LocalDateTime getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(LocalDateTime operatingTime) {
        this.operatingTime = operatingTime;
    }
}