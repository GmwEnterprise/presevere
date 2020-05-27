package com.github.mrag.wechat.im;

public enum EnumCommand {
    /**
     * 发一条消息给一个朋友
     */
    SEND_MESSAGE_TO_A_FRIEND(1),
    /**
     * 群发消息给多个朋友
     */
    SEND_MESSAGE_TO_MANY_FRIENDS(2);

    private final int command;

    EnumCommand(int command) {
        this.command = command;
    }

    public static EnumCommand of(int command) {
        for (EnumCommand cmd : values())
            if (cmd.command == command) return cmd;
        return null;
    }
}
