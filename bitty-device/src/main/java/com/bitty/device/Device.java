package com.bitty.device;

import com.bitty.proto.Broker;
import com.bitty.proto.Message;
import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Device {
    Container container=new Container();

    public static void main(String[] args) throws InterruptedException, IOException {
        log.info("启动容器");
        Device d=new Device();
        d.init(new DeviceProperty("/application.properties"));
    }

    public void   init(DeviceProperty deviceProperty){
        log.info("启动设备 {} ",deviceProperty.getToken());
        container.init(deviceProperty);
    }

    public void sendMessage(Integer targetId,byte[] payload) {
        log.info("给用户 target id  {} 发送消息 {}", targetId, ByteBufUtil.hexDump(Unpooled.buffer().writeBytes(payload)));
        container.client.sendMessage(targetId,payload);
    }
}