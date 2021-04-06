package com.bitty.common.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ClientAutoConnectHandler extends ChannelInboundHandlerAdapter {

    private Bootstrap bootstrap;

    public ClientAutoConnectHandler(Bootstrap bootstrap){
        this.bootstrap=bootstrap;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("处理重连逻辑");

//        bootstrap.connect(ctx.channel().remoteAddress()).sync();
    }
}
