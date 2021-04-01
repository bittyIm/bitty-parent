package com.bitty.standalone;

import com.bitty.broker.Broker;
import com.bitty.root.Root;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class StandAlone {

    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("启动root服务器");
        Root.main(args);
        log.info("启动broker服务器");
        Broker.main(args);
    }
}
