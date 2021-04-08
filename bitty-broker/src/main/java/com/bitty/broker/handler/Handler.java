package com.bitty.broker.handler;

import io.netty.channel.ChannelHandlerContext;

@FunctionalInterface
public interface Handler {
   void  Run(Object event, ChannelHandlerContext ctx, String[] payload);
}
