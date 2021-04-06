package com.bitty.device.local;

import com.bitty.codec.BittyDecoder;
import com.bitty.codec.BittyEncoder;
import com.bitty.proto.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class Client {
    Channel ch = null;

    public Client(Properties properties) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
                        pipeline.addLast(new BittyDecoder());
                        pipeline.addLast(new BittyEncoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<Message.MessageFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message.MessageFrame msg) {
                                log.info("处理回调消息" + msg);
                            }
                        });
                    }
                });
        try {
            ch = b.connect((String) properties.get("app.broker.server"), Integer.parseInt((String) properties.get("app.broker.port"))).sync().channel();

            var msg = Message.MessageFrame.newBuilder()
                    .setCreateAt(2000)
                    .setMessageId(1000)
                    .setPayload("hello broker")
                    .build()
                    .toByteArray();

            ChannelFuture lastWriteFuture = ch.writeAndFlush(msg).sync();
            lastWriteFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }
}
