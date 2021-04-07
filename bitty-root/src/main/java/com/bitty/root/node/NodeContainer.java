package com.bitty.root.node;

import com.bitty.common.BittyContainer;
import com.bitty.proto.Broker;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NodeContainer extends BittyContainer {
    /**
     * 所有节点的注册树
     */
    ConcurrentHashMap<Integer, Broker.signupNode> nodeList = new ConcurrentHashMap<>();

    public void signup(ChannelHandlerContext channelHandlerContext, Broker.MessageFrame msg) {
        try {
            var m = Broker.signupNode.parseFrom(msg.getPayload());
            log.info("写入注册树 node:{}  ip:{} port:{} ttl:{} reqId:{}", m.getNode(), m.getIp(), m.getPort(), m.getTtl(), msg.getReqId());
            nodeList.put(m.getNode(), m);
            success(channelHandlerContext, msg.getReqId());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void success(ChannelHandlerContext channelHandlerContext, Integer reqId) {
        log.info("回复 {}", reqId);
        var msg = Broker.MessageFrame.newBuilder()
                .setCmd(Broker.MessageFrame.Cmd.OK)
                .setReqId(reqId)
                .build();
        channelHandlerContext.writeAndFlush(msg);
    }
}
