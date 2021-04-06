package com.bitty.broker;

import com.bitty.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用层handler处理
 */
@Slf4j
public class BittyHandler extends SimpleChannelInboundHandler<Message.MessageFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message.MessageFrame messageFrame) throws Exception {
        log.info("判断本地用户");

        log.info("判断远程用户");

        log.info("放入本地缓存");

        log.info("查找对端用户所在的broker");

        log.info("执行路由");
        channelHandlerContext.writeAndFlush("hello device");
    }

}
