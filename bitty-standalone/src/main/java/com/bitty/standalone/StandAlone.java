package com.bitty.standalone;

import com.bitty.broker.Broker;
import com.bitty.broker.BrokerProperty;
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
                b.initStart(new BrokerProperty("/application.broker.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        brokerThread.start();

        System.in.read();

    }
}
