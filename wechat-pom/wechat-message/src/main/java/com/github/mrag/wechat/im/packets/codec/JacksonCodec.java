package com.github.mrag.wechat.im.packets.codec;

import com.github.mrag.wechat.im.packets.EncodingMode;
import com.github.mrag.wechat.util.JacksonUtil;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

@Component
public class JacksonCodec implements Codec {
    public JacksonCodec() {
        PacketCodec.setEncodingModeMap(EncodingMode.JSON, new JacksonCodec());
    }

    @Override
    public <T> byte[] encode(T body) throws Exception {
        return JacksonUtil.serialize(body).getBytes(CharsetUtil.UTF_8);
    }

    @Override
    public <T> T decode(byte[] in, Class<T> type) throws Exception {
        return JacksonUtil.deserialize(type, new String(in, CharsetUtil.UTF_8));
    }
}
