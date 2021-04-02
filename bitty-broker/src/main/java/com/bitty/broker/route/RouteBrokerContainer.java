package com.bitty.broker.route;


import com.bitty.common.BittyContainer;
import com.bitty.common.BittyRoute;

import java.util.concurrent.ConcurrentHashMap;

public class RouteBrokerContainer extends BittyContainer {

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
