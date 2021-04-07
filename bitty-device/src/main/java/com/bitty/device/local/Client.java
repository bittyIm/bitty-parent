package com.bitty.device.local;

import com.bitty.codec.BittyDecoder;
import com.bitty.codec.BittyEncoder;
import com.bitty.device.DeviceProperty;

import com.bitty.device.event.MessageEventImpl;

import com.bitty.event.Event;
import com.bitty.proto.Message;
import com.google.protobuf.ByteString;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
public class Client {
    Channel ch = null;

    AtomicInteger mId = new AtomicInteger(1);

    Event<Integer> event = null;

    HashMap<Integer, Message.MessageFrame> messages = new HashMap();

    public Client(DeviceProperty properties) {
        event = new MessageEventImpl(messages);
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 2, 4, 0, 0));
                        pipeline.addLast(new BittyDecoder());
                        pipeline.addLast(new BittyEncoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<Message.MessageFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message.MessageFrame msg) {
                                event.fire(channelHandlerContext, msg.getMessageId(), msg);
                            }
                        });
                    }
                });
        try {
            ch = b.connect(properties.getServerIp(), properties.getServerPort()).sync().channel();
            log.info("初始化设备链接到  {} {} ", properties.getServerIp(), properties.getServerPort());

            var msg = Message.MessageFrame.newBuilder()
                    .setMessageId(mId.getAndAdd(1))
                    .setCmd(Message.MessageFrame.Cmd.AUTH)
                    .setPayload(ByteString.copyFromUtf8(properties.getToken()))
                    .build();
            messages.put(msg.getMessageId(), msg);
            ChannelFuture lastWriteFuture = ch.writeAndFlush(msg).sync();
            lastWriteFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    public void sendMessage(Integer targetId, byte[] payload) {
        var msg = Message.MessageFrame.newBuilder().
                setCmd(Message.MessageFrame.Cmd.MESSAGE)
                .setMessageId(mId.getAndAdd(1))
                .setTargetId(targetId)
                .setPayload(ByteString.copyFrom(payload)).build();

        messages.put(msg.getMessageId(), msg);
        event.on(msg.getMessageId(), (source, id, p) -> {
            log.info("系统内部处理消息事件");
        });
        ch.writeAndFlush(msg);
    }
}
