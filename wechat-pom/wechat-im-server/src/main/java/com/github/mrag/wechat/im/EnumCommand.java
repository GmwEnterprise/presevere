package com.github.mrag.wechat.im;

public enum EnumCommand {
    /**
     * 发送单条消息给一个用户
     */
    SINGLE_MSG_TO_ONE_USER(1),
    /**
     * 发送单条消息给多个用户
     */
    SINGLE_MSG_TO_MANY_USER(2);

    private final int command;

    EnumCommand(int command) {
        this.command = command;
    }

    public static EnumCommand of(int command) {
        for (EnumCommand cmd : values()) {
            if (cmd.command == command) {
                return cmd;
            }
        }
        return null;
    }

    public int getCommand() {
        return command;
    }
}
