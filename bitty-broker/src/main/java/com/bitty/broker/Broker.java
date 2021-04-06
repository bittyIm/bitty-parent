package com.bitty.broker;

import com.bitty.codec.BittyDecoder;
import com.bitty.codec.BittyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Broker {
    public static BrokerContainer brokerContainer = new BrokerContainer();
    public static void main(String[] args) throws InterruptedException, IOException {

        brokerContainer.initProperty();


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
                            pipeline.addLast(new BittyDecoder());
                            pipeline.addLast(new BittyEncoder());
                            pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new BittyHandler());
                        }
                    });

            log.info("开始监听 {} {} ", brokerContainer.getProperties().getProperty("app.broker.server"), brokerContainer.getProperties().getProperty("app.broker.port"));

            ChannelFuture f = b.bind((String) (brokerContainer.getProperties().getProperty("app.broker.server")),
                    Integer.parseInt(brokerContainer.getProperties().getProperty("app.broker.port")));

            f.addListener(g->{

                brokerContainer.signin();
            });
            f.channel().closeFuture().sync();
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
