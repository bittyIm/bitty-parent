package com.bitty.device;

import com.bitty.common.BittyContainer;
import com.bitty.common.handler.EchoHandler;
import com.bitty.device.local.Client;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Data
@Slf4j
public class Container<T> extends BittyContainer {

    Client client=null;

    Network network;
    /**
     * 容器同步游标
     */
    Long offset;

    Map<T, Ap> accessPointList = new HashMap<T, Ap>();

    /**
     * 同步用户列表
     */
    void syncAccessPointList(){
        log.info("本地缓存恢复");
    }

    void setNetWork(Network netWork){
        this.network=netWork;
    }

    Network getNetwork(){
        return null;
    }

    protected void initLocalClient() {
        log.info("初始化服务器连接");
        client=new Client(getProperties());
    }

    protected void initLocalNetwork() {
        if(  Boolean.parseBoolean((getProperties().getProperty("app.enableLocalNetwork")))){
            log.info("初始化本地网路");
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
                                p.addLast(new DelimiterBasedFrameDecoder(2048, true, Unpooled.copiedBuffer("\n".getBytes())));
                                p.addLast(new StringDecoder());
                                p.addLast(new StringEncoder());
                                p.addLast(new LoggingHandler(LogLevel.INFO));
                                p.addLast(new EchoHandler());
                            }
                        });
                ChannelFuture f = b.bind(Integer.parseInt(getProperties().getProperty("app.port")));
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }
    }

    public void init() throws IOException {
        super.initProperty();
        initLocalClient();
        initLocalNetwork();
        syncAccessPointList();
    }
}
