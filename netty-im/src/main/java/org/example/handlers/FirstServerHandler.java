package org.example.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

@ChannelHandler.Sharable
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收数据
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端收到数据 -> " + buf.toString(CharsetUtil.UTF_8));

        // 回复数据
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf returnBuf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(returnBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes();
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }
}
