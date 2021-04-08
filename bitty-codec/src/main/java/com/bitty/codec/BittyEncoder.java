package com.bitty.codec;

import com.bitty.proto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BittyEncoder extends MessageToByteEncoder<Message.MessageFrame> {
    // 00000001
    byte magicPre = 0x7f;
    //版本 0 and 序列化方式
    // 00010001
    //多版本支持
    byte version = 0x05;


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message.MessageFrame msg, ByteBuf byteBuf) throws Exception {
        byte[] magic = new byte[]{magicPre, version};
        ByteBuf buf = Unpooled.buffer();
        var source = E.enc(msg.toByteArray());
        buf.writeBytes(magic);
        buf.writeInt(source.length);
        buf.writeBytes(source);
        byteBuf.writeBytes(buf);
        channelHandlerContext.flush();
    }

}
