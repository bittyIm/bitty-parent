package com.bitty.root.handler;

import com.bitty.anotation.CMD;

import com.bitty.proto.Message;
import com.bitty.root.Handler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@CMD(value = Message.MessageFrame.Cmd.AUTH)
@Slf4j
public class AuthHandler implements Handler {
    @Override
    public void Run(Object event, ChannelHandlerContext ctx, String[] payload) {
        log.info("用户授权的时候执行");
    }
}
