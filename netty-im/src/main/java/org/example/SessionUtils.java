package org.example;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.Attributes.SESSION;

public class SessionUtils {
    // userId -> channel
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.putIfAbsent(session.getUserId(), channel);
        channel.attr(SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (channel.hasAttr(SESSION)) {
            userIdChannelMap.remove(channel.attr(SESSION).get().getUserId());
            channel.attr(SESSION).set(null);
        }
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }
}
