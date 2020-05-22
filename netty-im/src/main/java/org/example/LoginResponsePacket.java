package org.example;

public class LoginResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
