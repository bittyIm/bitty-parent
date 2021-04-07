package com.bitty.root;

import com.bitty.proto.Broker;
import com.bitty.root.node.NodeContainer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 应用层handler处理
 */
@Slf4j
public class BittyRootHandler extends SimpleChannelInboundHandler<Broker.MessageFrame> {

    NodeContainer nodeContainer;

    public BittyRootHandler(NodeContainer nodeContainer) {
        this.nodeContainer=nodeContainer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Broker.MessageFrame msg) throws Exception {
        log.info("消息类型 {} id: {}", msg.getCmd(),msg.getReqId());
        if(msg.getCmd()==Broker.MessageFrame.Cmd.signup){
            nodeContainer.signup(channelHandlerContext,msg);
            return;
        }
    }
}
