package com.bitty.broker;

import com.bitty.codec.BittyDecoder;
import com.bitty.codec.BittyEncoder;
import com.bitty.common.handler.BittyHeartBeatServerHandler;
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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class Broker {

    BrokerContainer brokerContainer = new BrokerContainer();

    BrokerProperty property = new BrokerProperty();

    EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ChannelFuture f = null;

    public static void main(String[] args) throws IOException, InterruptedException {
        Broker b = new Broker();
        b.initStart(new BrokerProperty("/application.properties"));
        System.in.read();
    }

    public void initStart(BrokerProperty property) {
        this.property = property;

        log.info("启动broker服务器 {} {} ",property.getServerIp(),property.getServerPort());

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(60, 60, 5, TimeUnit.SECONDS));
                            pipeline.addLast(new BittyDecoder());
                            pipeline.addLast(new BittyEncoder());
                            pipeline.addLast(new BittyHeartBeatServerHandler());
                            pipeline.addLast(new BittyHandler(brokerContainer));
                        }
                    });

            f = b.bind(property.getServerIp(), property.getServerPort());

            f.addListener(g -> {
                brokerContainer.signin(this);
            });

            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
