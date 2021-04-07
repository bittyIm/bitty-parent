package com.bitty.broker.route;

import com.bitty.broker.BrokerContainer;
import com.bitty.codec.BittyBrokerDecoder;
import com.bitty.codec.BittyBrokerEncoder;
import com.bitty.common.BittyRoute;
import com.bitty.proto.Broker;
import com.bitty.proto.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class BittyRouteImpl implements BittyRoute {

    private final BrokerContainer brokerContainer;

    Channel ch = null;


    public void   initClient(Broker.resolveNode msg, BittyRouteImpl bittyRoute){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,2,4,0,0));
                        pipeline.addLast(new BittyBrokerEncoder());
                        pipeline.addLast(new BittyBrokerDecoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<Broker.MessageFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Broker.MessageFrame msg) {
                                log.info("处理回调消息" + msg);
                                try {
                                    if(msg.getCmd().equals(Broker.MessageFrame.Cmd.resolveNode)){
                                        log.info("找到节点---");
                                        brokerContainer.resolveNodeCallback(Broker.resolveNode.parseFrom(msg.getPayload()));
                                    }
                                } catch (InvalidProtocolBufferException e) {
                                    log.info("解析异常");
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });
        try {
            ch = b.connect(msg.getIp(),msg.getPort()).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public BittyRouteImpl(Broker.resolveNode msg, BrokerContainer brokerContainer) {
        this.brokerContainer=brokerContainer;
        initClient(msg,this);
    }

    @Override
    public void forward(Message.MessageFrame messageFrame) {

    }

    @Override
    public Integer getTargetNodeId() {
        return null;
    }
}
