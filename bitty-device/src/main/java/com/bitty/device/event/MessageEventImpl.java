package com.bitty.device.event;


import com.bitty.event.Event;
import com.bitty.event.Listener;
import com.bitty.proto.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MessageEventImpl implements Event<Integer> {

    /**
     * cmd 事件
     */
    Event<String> cmdEvent = new CmdEventImpl();

    HashMap<Integer, Message.MessageFrame> messages;


    Map<Integer, Listener<Integer>> events = new ConcurrentHashMap<>();

    public MessageEventImpl(HashMap<Integer, Message.MessageFrame> messages) {
        this.messages = messages;
    }


    @Override
    public void on(Integer mid, Listener<Integer> listener) {
        events.put(mid, listener);
    }

    @Override
    public void fire(Object source, Integer mid, Object payload) {
        var ev = events.get(mid);
        var p = (Message.MessageFrame) payload;
        if (ev != null) {
            events.get(mid).run(source, mid, payload);
            events.remove(mid);
        }
        cmdEvent.fire(source, messages.get(mid).getCmd() + "_" + p.getCmd(), payload);
    }

}
