package com.bitty.codec;

import com.bitty.common.handler.BittyHeartBeatServerHandler;
import com.bitty.common.handler.ClientAutoConnectHandler;
import com.bitty.proto.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
class BittyDecoderTest {

    static Properties properties = new Properties();

    @BeforeAll
    static void startServer() throws IOException, ClassNotFoundException, InterruptedException {
        log.info("start test server");
        final InputStream stream = BittyDecoderTest.class.getResourceAsStream("/application.test.properties");
        properties.load(stream);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        var t = new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new IdleStateHandler(60,60,5, TimeUnit.SECONDS));
                                pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,2,4,0,0));
                                pipeline.addLast(new BittyDecoder());
                                pipeline.addLast(new BittyEncoder());
                                pipeline.addLast(new BittyHeartBeatServerHandler());
                                pipeline.addLast(new SimpleChannelInboundHandler<Message.MessageFrame>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, Message.MessageFrame message) throws Exception {
                                        log.info("server receive:"+message.getPayload());
                                        ctx.writeAndFlush(message).sync();
                                    }
                                });
                            }
                        });

                ChannelFuture f = b.bind((String) (properties.getProperty("app.test.server")),
                        Integer.parseInt(properties.getProperty("app.test.port")));
                f.addListener(s -> {
                    log.info("server start success");
                    countDownLatch.countDown();
                });
                f.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });

        t.start();

        countDownLatch.await();
    }

    @Test
    void channelRead0() {
        log.info("start test client");
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,2,4,0,0));
                        pipeline.addLast(new BittyDecoder());
                        pipeline.addLast(new BittyEncoder());
                        pipeline.addLast(new ClientAutoConnectHandler(b));
                        pipeline.addLast(new SimpleChannelInboundHandler<Message.MessageFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message.MessageFrame msg) throws InterruptedException {
                                log.info("success" + msg.getPayload());
                                countDownLatch.countDown();
                            }
                        });
                    }
                });
        try {
            Channel ch = b.connect((String) properties.get("app.test.server"), Integer.parseInt((String) properties.get("app.test.port"))).sync().channel();

            var payload = Message.MessageFrame.newBuilder()
                    .setCreateAt(2000)
                    .setMessageId(1000)
                    .setPayload("hello encoder")
                    .build();

            ChannelFuture lastWriteFuture = ch.writeAndFlush(payload).sync();
            lastWriteFuture.sync();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }

    }
}