package com.bitty.root;

import com.bitty.codec.BittyBrokerDecoder;
import com.bitty.codec.BittyBrokerEncoder;
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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Root {

    RootProperty rootProperty;

    EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ChannelFuture f=null;

    public static void main(String[] args) throws InterruptedException, IOException {
        var root=new Root();
        root.initStart(new RootProperty("/application.properties"));
    }
    public void initStart(RootProperty property) throws InterruptedException, IOException {
        this.rootProperty=property;
        log.info("启动root服务器 {} {} ",property.getRootIp(),property.getRootPort());
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,0));
//                            p.addLast(new IdleStateHandler(60, 60, 5, TimeUnit.SECONDS));
                            p.addLast(new BittyBrokerEncoder());
                            p.addLast(new BittyBrokerDecoder());
                            p.addLast(new BittyRootHandler());
//                            p.addLast(new BittyHeartBeatServerHandler());
                        }
                    });

            f = b.bind(property.getRootIp(),property.getRootPort());
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}