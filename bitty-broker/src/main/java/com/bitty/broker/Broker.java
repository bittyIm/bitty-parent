package com.bitty.broker;

import com.bitty.broker.boot.Boot;
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

        Boot boot=new Boot(property);

        log.info("启动broker服务器 {} {} {}",property.getServerIp(),property.getServerPort(),property.getNodeId());

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 2, 4, 0, 0));
                            pipeline.addLast(new IdleStateHandler(60, 60, 5, TimeUnit.SECONDS));
                            pipeline.addLast(new BittyDecoder());
                            pipeline.addLast(new BittyEncoder());
                            pipeline.addLast(new BittyMessageHandler(brokerContainer));
                            pipeline.addLast(new BittyHeartBeatServerHandler());
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
