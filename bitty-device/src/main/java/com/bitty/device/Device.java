package com.bitty.device;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Device {
    public static void main(String[] args) throws InterruptedException, IOException {
        log.info("启动容器");
        Container container=new Container();
        container.init(new DeviceProperty("/application.properties"));
    }
}