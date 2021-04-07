package com.bitty.standalone;

import com.bitty.device.Device;
import com.bitty.device.DeviceProperty;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StandAloneTest {

    @Test
    void main() throws IOException {
        var deviceThread=new Thread(() -> {

            Device d1=new Device();
            try {
                d1.init(new DeviceProperty("/application.device0.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deviceThread.start();

        System.in.read();
    }
}