package com.bitty.codec;

import com.bitty.proto.Broker;
import com.bitty.proto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class BittyBrokerEncoder extends MessageToByteEncoder<Broker.MessageFrame> {


    public BittyBrokerEncoder() throws NoSuchAlgorithmException {

    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Broker.MessageFrame msg, ByteBuf out) throws Exception {

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msg.toByteArray().length);

        var source = msg.toByteArray();
        buf.writeBytes(source);

        log.info("编码 -> [{}] {}", msg.toByteArray().length,ByteBufUtil.hexDump(source));

        out.writeBytes(buf);
        channelHandlerContext.flush();
    }

}
