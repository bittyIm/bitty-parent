package com.bitty.standalone;

import com.bitty.device.Device;
import com.bitty.device.DeviceProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
class StandAloneTest1 {

    @Test
    void main() throws IOException {
        var deviceThread=new Thread(() -> {
            Device d1=new Device();
            try {
                d1.init(new DeviceProperty("/application.device1.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            var i=0;
            var start=System.currentTimeMillis();
            while (i<1){
                d1.sendMessage(1,"你好呀0001用户".getBytes());
                i++;
            }
            var end=System.currentTimeMillis();
            log.info("GAP {}",end-start);

        });
        deviceThread.start();


        System.in.read();
    }
}