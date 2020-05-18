package org.example;

import java.time.LocalDateTime;

public final class Console {

    public static void log(String content) {
        System.out.printf("[%-29s] - [%-50s]: %s%n", LocalDateTime.now(), Thread.currentThread(), content);
    }
}
