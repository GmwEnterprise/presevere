package org.example.packet;

import org.example.Command;

public class LoginResponsePacket extends Packet {
    private Boolean success;
    private String reason;
    private String userId;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
