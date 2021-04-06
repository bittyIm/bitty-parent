package com.bitty.codec;

import com.bitty.proto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class BittyEncoder extends MessageToByteEncoder<Message.MessageFrame> {

    AtomicInteger mid=new AtomicInteger(0);

    Long startAt=0L;

    public BittyEncoder() throws NoSuchAlgorithmException {

    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message.MessageFrame msg, ByteBuf byteBuf) throws Exception {
        byte[] magic = new byte[]{0x01, 0x00};

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(magic);
//        buf.writeByte(mid.addAndGet(1));
//        buf.writeByte(mid.addAndGet(1));
//        buf.writeByte()
        buf.writeInt(msg.toByteArray().length);
        var source=msg.toByteArray();
        buf.writeBytes(source);
//        log.info(ByteBufUtil.prettyHexDump(buf));
        byteBuf.writeBytes(buf);
        channelHandlerContext.flush();
    }

}
