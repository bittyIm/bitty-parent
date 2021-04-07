package com.bitty.broker;

import com.bitty.common.BittyRoute;

@FunctionalInterface
public interface ResolveCallback {
    void   callback(BittyRoute r);
}
