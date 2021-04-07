package com.bitty.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReqEventImpl implements Event<Integer> {

    Map<Integer, Listener<Integer>> events = new ConcurrentHashMap<>();

    @Override
    public void on(Integer reqId, Listener listener) {
        events.put(reqId, listener);
    }

    @Override
    public void fire(Object source, Integer reqId, Object payload) {
        events.get(reqId).run(source, reqId, payload);
        events.remove(reqId);
    }
}
