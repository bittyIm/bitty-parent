package com.bitty.broker.client;

import com.bitty.codec.BittyBrokerDecoder;
import com.bitty.codec.BittyBrokerEncoder;
import com.bitty.proto.Broker;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RootClient {
    AtomicInteger id=new AtomicInteger(1);
    Channel ch = null;

    public RootClient(com.bitty.broker.Broker broker) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 0));
                        pipeline.addLast(new BittyBrokerEncoder());
                        pipeline.addLast(new BittyBrokerDecoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<Broker.MessageFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Broker.MessageFrame msg) {
                                try {
                                    if (msg.getCmd().equals(Broker.MessageFrame.Cmd.resolveNode)) {
                                        log.info("找到节点---");
                                        broker.getBrokerContainer().resolveNodeCallback(Broker.resolveNode.parseFrom(msg.getPayload()));
                                    }
                                    if(msg.getCmd().equals(Broker.MessageFrame.Cmd.OK)){
                                        log.info("成功消息回调 {}",msg.getReqId());

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
            log.info("连接到root server {} {} {}", broker.getProperty().getRootIp(), broker.getProperty().getRootPort(),broker.getProperty().getNodeId());
            ch = b.connect(broker.getProperty().getRootIp(), broker.getProperty().getRootPort()).sync().channel();

            var sign = Broker.signupNode.newBuilder()
                    .setIp(broker.getProperty().getNetworkIp())
                    .setPort(broker.getProperty().getServerPort())
                    .setNode(broker.getProperty().getNodeId())
                    .setTtl(broker.getProperty().getTtl())
                    .build();
            log.info("发送注册消息: {}", ByteBufUtil.hexDump(sign.toByteArray()));
            var msg = Broker.MessageFrame.newBuilder()
                    .setReqId(id.getAndAdd(1))
                    .setCmd(Broker.MessageFrame.Cmd.signup)
                    .setPayload( ByteString.copyFrom(sign.toByteArray())).build();

            ch.writeAndFlush(msg);

        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public void resolve(Integer bittyNodeId) {
        //查找节点
//        var msg = Broker.MessageFrame.newBuilder()
//                .setCmd(Broker.MessageFrame.Cmd.resolveNode)
//                .setPayload(String.valueOf(bittyNodeId)).build();
//        ch.writeAndFlush(msg);
    }
}
