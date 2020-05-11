package me.persevere.demo.nio1.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelDemo {

    public static void main(String[] args) throws IOException {
        // 创建一个UDP通道并绑定一个本地端口
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(true); // 阻塞模式
        dc.socket().bind(new InetSocketAddress(9999));

        // 接收数据
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        dc.receive(buf); // 如果有数据报文，超过buf长度的部分将静默丢弃

        // 发送数据。 udp协议不保证数据一定会发送到，所以该方法不会产生任何发送状态信息
        dc.send(buf, new InetSocketAddress("www.baidu.com", 8888));
    }
}
