package com.bitty.codec;

import com.bitty.proto.Broker;
import com.bitty.proto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BittyBrokerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        var length = byteBuf.readInt();
        var d = new byte[length];
        byteBuf.readBytes(d);
        log.info("解码 -> [{}] {}",length, ByteBufUtil.hexDump(d));
        try {
            Broker.MessageFrame ms = Broker.MessageFrame
                    .newBuilder()
                    .mergeFrom(d)
                    .build();
            list.add(ms);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
