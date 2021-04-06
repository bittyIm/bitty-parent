package com.bitty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import com.bitty.proto.Message;

import java.util.List;

@Slf4j
public class BittyDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        var magicCode = new byte[2];
        byteBuf.readBytes(magicCode);
        var length = byteBuf.readInt();
        var d = new byte[length];
        byteBuf.readBytes(d);
        Message.MessageFrame ms = Message.MessageFrame
                .newBuilder()
                .mergeFrom(d)
                .build();
        list.add(ms);
    }
}
