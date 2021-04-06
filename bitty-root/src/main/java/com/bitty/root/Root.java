package com.bitty.root;

import com.bitty.codec.BittyDecoder;
import com.bitty.codec.BittyEncoder;
import com.bitty.common.handler.BittyHeartBeatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Root {

    public static void main(String[] args) throws InterruptedException, IOException {
        Container container = new Container();
        container.initProperty();
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
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IdleStateHandler(60, 60, 5, TimeUnit.SECONDS));
                            p.addLast(new BittyDecoder());
                            p.addLast(new BittyEncoder());
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new BittyHeartBeatServerHandler());
                            p.addLast(new BittyRootHandler());
                        }
                    });
            log.info("开始监听 {} {} ", container.getProperties().getProperty("app.root.server"), container.getProperties().getProperty("app.root.port"));
            ChannelFuture f = b.bind((String) (container.getProperties().getProperty("app.root.server")),
                    Integer.parseInt(container.getProperties().getProperty("app.root.port")));
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}