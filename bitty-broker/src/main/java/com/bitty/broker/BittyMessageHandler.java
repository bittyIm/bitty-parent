package com.bitty.broker;

import com.bitty.proto.Broker;
import com.bitty.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用层handler处理
 */
@Slf4j
public class BittyMessageHandler extends SimpleChannelInboundHandler<Message.MessageFrame> {

    BrokerContainer bittyContainer;

    public BittyMessageHandler(BrokerContainer brokerContainer) {
        this.bittyContainer=brokerContainer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message.MessageFrame msg) throws Exception {

        if(msg.getCmd()== Message.MessageFrame.Cmd.AUTH){
            log.info("收到注册消息 {}",msg.getPayload().toStringUtf8());
            success(channelHandlerContext,msg.getMessageId());
            return;
        }
        if(msg.getCmd()== Message.MessageFrame.Cmd.MESSAGE){
            log.info("给其他用户发送消息 {}",msg);
            success(channelHandlerContext,msg.getMessageId());
        }
        if(msg.getCmd()== Message.MessageFrame.Cmd.SYNC){
            log.info("用户要同步配置信息");
        }
    }

    private void success(ChannelHandlerContext channelHandlerContext, Integer reqId) {
        log.info("回复 {}", reqId);
        var msg = com.bitty.proto.Message.MessageFrame.newBuilder()
                .setCmd(Message.MessageFrame.Cmd.OK)
                .setMessageId(reqId)
                .build();
        channelHandlerContext.writeAndFlush(msg);
    }
}
