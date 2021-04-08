package com.bitty.broker.plugins;

import com.bitty.broker.handler.Handler;
import com.bitty.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import com.bitty.anotation.*;

@CMD(value = Message.MessageFrame.Cmd.AUTH)
@Slf4j
public class AuthPlugin implements Handler {
    @Override
    public void Run(Object event, ChannelHandlerContext ctx, String[] payload) {
        log.info("auth的时候执行");
    }
}
