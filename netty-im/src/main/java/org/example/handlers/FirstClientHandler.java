package org.example.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

@ChannelHandler.Sharable
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 这个时候连接已经就绪，可以写数据/读数据了
        System.out.println(new Date() + ": 客户端写出数据");
        ByteBuf buf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(buf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes("你好! 闪电侠!".getBytes());
        return buf;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(CharsetUtil.UTF_8));
    }
}
