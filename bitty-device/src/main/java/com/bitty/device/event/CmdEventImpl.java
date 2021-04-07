package com.bitty.device.event;


import com.bitty.event.Event;
import com.bitty.event.Listener;
import com.bitty.proto.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Slf4j
public class CmdEventImpl implements Event<String> {

    HashMap<String, List<Listener>> events = new HashMap<>();

    @Override
    public void on(String name, Listener<String> listener) {
        events.get(name).add(listener);
    }

    @Override
    public void fire(Object source,String name, Object payload) {
        log.info("触发CMD事件 {}",name);
        var listeners=events.get(name);
        if(listeners!=null){
            listeners.forEach(l -> l.run(source, name, payload));
        }
    }
}
