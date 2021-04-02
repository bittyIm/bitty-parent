package com.bitty.broker.route;

import com.bitty.broker.Container;
import com.bitty.common.BittyRoute;

import java.util.concurrent.ConcurrentHashMap;

public class RouteContainer extends Container {

    ConcurrentHashMap<Integer, BittyRoute> routeCache = new ConcurrentHashMap<>();

    BittyRoute getRoute(Integer bittyNodeId) {
        BittyRoute route = routeCache.get(bittyNodeId);
        if (route == null) {
            route = resolve(bittyNodeId);
        }
        return route;
    }

    private BittyRoute resolve(Integer bittyNodeId) {
        return null;
    }
}
