package com.bitty.event;

public interface Listener<T> {
    void run(Object source,T name,Object payload);
}
