package com.bitty.common.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdleStateHandler extends ChannelInboundHandlerAdapter {

    private Bootstrap bootstrap;

    public IdleStateHandler(Bootstrap bootstrap){
        this.bootstrap=bootstrap;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("重新连接");
        bootstrap.connect(bootstrap.config().remoteAddress()).sync();
    }
}
