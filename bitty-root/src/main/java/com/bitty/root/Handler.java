package com.bitty.root;

import io.netty.channel.ChannelHandlerContext;

@FunctionalInterface
public interface Handler {
   void  Run(Object event, ChannelHandlerContext ctx, String[] payload);
}
