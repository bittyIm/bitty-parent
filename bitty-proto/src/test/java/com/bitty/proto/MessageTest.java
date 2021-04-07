package com.bitty.proto;


import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class MessageTest {
    @Test
    public void test1() throws InvalidProtocolBufferException {

        byte[] m = Message.MessageFrame.newBuilder()
                .setPayload("hello bitty")
                .build()
                .toByteArray();

        log.info(ByteBufUtil.prettyHexDump(Unpooled.buffer().writeBytes(m)));

        Message.MessageFrame ms = Message.MessageFrame
                .newBuilder()
                .mergeFrom(m)
                .build();

        log.info(ms.getPayload());
    }
}