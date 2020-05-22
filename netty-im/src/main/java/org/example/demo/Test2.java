package org.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Test2 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.directBuffer(5);
        assert buf.refCnt() == 1;

        System.out.println(buf.release());
        byte b = buf.readByte();
        System.out.println(b);
    }
}
