package org.example;

import io.netty.channel.Channel;

public class LoginUtils {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Boolean loginFlag = channel.attr(Attributes.LOGIN).get();
        return loginFlag != null && loginFlag;
    }
}
