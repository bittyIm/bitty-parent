package com.bitty.broker;

import com.bitty.common.BittyContainer;
import com.bitty.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用层handler处理
 */
@Slf4j
public class BittyHandler extends SimpleChannelInboundHandler<Message.MessageFrame> {

    BrokerContainer bittyContainer;

    public BittyHandler(BrokerContainer brokerContainer) {
        this.bittyContainer=brokerContainer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message.MessageFrame messageFrame) throws Exception {
        this.bittyContainer.forwordMessage(messageFrame);
    }

}
