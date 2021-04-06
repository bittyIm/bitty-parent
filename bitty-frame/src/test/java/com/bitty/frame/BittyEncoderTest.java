package com.bitty.frame;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BittyEncoderTest {
    @Test
    void encode() throws NoSuchAlgorithmException {
        byte mid=0;
        byte[] data="hello bitty".getBytes();
        var encoder=new BittyEncoder();
        int i=0;

        var startAt=System.currentTimeMillis();
        while (i<100) {
            log.info(ByteBufUtil.hexDump(encoder.encode(data)));
            i++;
        }
        var endAt=System.currentTimeMillis();
        log.info("GAP: "+(endAt-startAt));
    }
}