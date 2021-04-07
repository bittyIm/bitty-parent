package com.bitty.broker.client;

import com.bitty.broker.BrokerContainer;
import com.bitty.broker.BrokerProperty;
import com.bitty.broker.ResolveCallback;
import com.bitty.codec.BittyBrokerDecoder;
import com.bitty.codec.BittyBrokerEncoder;
import com.bitty.common.BittyRoute;
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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class RootClient {
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
                                log.info("处理回调消息" + msg);
                                try {
                                    if (msg.getCmd().equals(Broker.MessageFrame.Cmd.resolveNode)) {
                                        log.info("找到节点---");
                                        broker.getBrokerContainer().resolveNodeCallback(Broker.resolveNode.parseFrom(msg.getPayload()));
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
            log.info("连接到root server {} {} ", broker.getProperty().getRootIp(), broker.getProperty().getRootPort());
            ch = b.connect(broker.getProperty().getRootIp(), broker.getProperty().getRootPort()).sync().channel();

            var sign = Broker.signupNode.newBuilder().setIp(broker.getProperty().getServerIp()).setPort(broker.getProperty().getServerPort()).build();
            log.info("发送注册消息: {}", ByteBufUtil.hexDump(sign.toByteArray()));
            var msg = Broker.MessageFrame.newBuilder()
                    .setCmd(Broker.MessageFrame.Cmd.signup)
                    .setPayload( ByteString.copyFrom(sign.toByteArray())).build();



            ch.writeAndFlush(msg).sync();
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
