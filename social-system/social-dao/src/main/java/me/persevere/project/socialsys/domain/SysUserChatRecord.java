package me.persevere.project.socialsys.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sys_user_chat_record
 * @author 
 */
public class SysUserChatRecord implements Serializable {
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
     * 文本消息/图片路径
     */
    private Byte messageType;

    /**
     * 消息主体/图片路径
     */
    private String message;

    /**
     * 消息发送状态：未知/成功/网络原因失败/对方拒收
     */
    private Byte sendStatus;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 对方实际接收时间
     */
    private LocalDateTime receiveTime;

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

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }
}