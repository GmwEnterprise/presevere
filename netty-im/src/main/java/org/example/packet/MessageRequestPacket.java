package org.example.packet;

import static org.example.Command.MESSAGE_REQUEST;

public class MessageRequestPacket extends Packet {
    private String to;
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
