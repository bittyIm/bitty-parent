package com.bitty.standalone;

import com.bitty.broker.Broker;
import com.bitty.broker.BrokerProperty;
import com.bitty.device.Device;
import com.bitty.device.DeviceProperty;
import com.bitty.root.Root;
import com.bitty.root.RootProperty;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class StandAlone {

    public static void main(String[] args) throws IOException, InterruptedException {

        var rootThread=new Thread(() -> {

            Root r=new Root();
            try {
                r.initStart(new RootProperty("/application.root.properties"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        rootThread.start();

        var brokerThread=new Thread(() -> {

            Broker b=new Broker();
            try {
                b.initStart(new BrokerProperty("/application.broker0.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        brokerThread.start();


        var brokerThread1=new Thread(() -> {

            Broker b1=new Broker();
            try {
                b1.initStart(new BrokerProperty("/application.broker1.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        brokerThread1.start();

        System.in.read();

    }
}
