package com.bitty.codec;

import com.bitty.proto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BittyEncoder extends MessageToByteEncoder<Message.MessageFrame> {



    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message.MessageFrame msg, ByteBuf byteBuf) throws Exception {
        byte[] magic = new byte[]{0x01, 0x00};
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(magic);
        buf.writeInt(msg.toByteArray().length);
        var source=msg.toByteArray();
        buf.writeBytes(source);
        byteBuf.writeBytes(buf);
        channelHandlerContext.flush();
    }

}
