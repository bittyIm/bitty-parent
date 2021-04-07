package com.bitty.common.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

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
