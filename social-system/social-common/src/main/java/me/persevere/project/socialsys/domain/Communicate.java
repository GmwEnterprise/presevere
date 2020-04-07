package me.persevere.project.socialsys.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * communicate
 * @author 
 */
public class Communicate implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发起者
     */
    private Long fromUser;

    /**
     * 接收者
     */
    private Long toUser;

    /**
     * 消息类型 1-添加好友，2-聊天消息
     */
    private Byte msgType;

    /**
     * 消息主体 验证消息 或 聊天消息
     */
    private String msgBody;

    /**
     * 响应状态 0-等待响应，1-添加好友成功，2-添加好友失败，3-消息发送成功，4-消息发送失败
     */
    private Byte responseType;

    /**
     * 消息发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 消息响应时间
     */
    private LocalDateTime responseTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUser() {
        return fromUser;
    }

    public void setFromUser(Long fromUser) {
        this.fromUser = fromUser;
    }

    public Long getToUser() {
        return toUser;
    }

    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Byte getResponseType() {
        return responseType;
    }

    public void setResponseType(Byte responseType) {
        this.responseType = responseType;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
    }
}