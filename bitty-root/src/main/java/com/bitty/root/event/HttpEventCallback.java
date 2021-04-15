package com.bitty.root.event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpEventCallback implements EventCallback {

    @Override
    public void run(Object payload) {
        log.info("调用http请求");
    }
}
