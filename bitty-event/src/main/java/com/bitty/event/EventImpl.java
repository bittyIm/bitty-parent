package com.bitty.event;


import java.util.HashMap;
import java.util.List;

public class EventImpl implements Event<String> {

    HashMap<String, List<Listener>> events = new HashMap<String, List<Listener>>();

    @Override
    public void on(String name, Listener listener) {
        events.get(name).add(listener);
    }

    @Override
    public void fire(Object source, String name, Object payload) {
        events.get(name).stream().forEach(l -> {
            l.run(source, name, payload);
        });
    }
}
