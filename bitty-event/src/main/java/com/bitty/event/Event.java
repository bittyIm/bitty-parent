package com.bitty.event;

public interface Event<T> {
    void on(T name, Listener<T> listener);
    void fire(Object source,T name,Object payload);
}
