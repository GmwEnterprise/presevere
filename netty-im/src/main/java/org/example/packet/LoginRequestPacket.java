package org.example.packet;

import static org.example.Command.LOGIN_REQUEST;

public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;

    @Override
    public String toString() {
        return String.format("[%s/%s/******]", userId, username);
    }

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
