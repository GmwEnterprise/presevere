package me.persevere.project.socialsys.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sys_user_relation
 * @author 
 */
public class SysUserRelation implements Serializable {
    private Integer id;

    /**
     * 发起者
     */
    private Integer fromUser;

    /**
     * 接收者
     */
    private Integer toUser;

    /**
     * 好友请求/同意添加好友/拒绝添加好友/黑名单
     */
    private Byte behavior;

    /**
     * 对方同意加好友/对方拒绝加好友
     */
    private Byte result;

    /**
     * 关系成立时间
     */
    private LocalDateTime buildTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUser() {
        return fromUser;
    }

    public void setFromUser(Integer fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getToUser() {
        return toUser;
    }

    public void setToUser(Integer toUser) {
        this.toUser = toUser;
    }

    public Byte getBehavior() {
        return behavior;
    }

    public void setBehavior(Byte behavior) {
        this.behavior = behavior;
    }

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public LocalDateTime getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(LocalDateTime buildTime) {
        this.buildTime = buildTime;
    }
}