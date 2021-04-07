package com.bitty.root;

import com.bitty.proto.Broker;
import com.bitty.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用层handler处理
 */
@Slf4j
public class BittyRootHandler extends SimpleChannelInboundHandler<Broker.MessageFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Broker.MessageFrame msg) throws Exception {
        log.info("消息类型 {}", msg.getCmd());
        log.info("判断本地用户");
        log.info("判断远程用户");
        log.info("放入本地缓存");
        log.info("查找对端用户所在的broker");
        log.info("执行路由");
//        channelHandlerContext.writeAndFlush("hello broker");
    }
}
