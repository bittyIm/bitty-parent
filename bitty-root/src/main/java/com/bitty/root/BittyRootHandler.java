package com.bitty.root;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用层handler处理
 */
@Slf4j
public class BittyRootHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("hello im bitty root\r\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        log.info("判断本地用户");

        log.info("判断远程用户");

        log.info("放入本地缓存");

        log.info("查找对端用户所在的broker");

        log.info("执行路由");
        channelHandlerContext.writeAndFlush("hello broker");
    }
}
