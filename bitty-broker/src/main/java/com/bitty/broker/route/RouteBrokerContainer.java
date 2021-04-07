package com.bitty.broker.route;


import com.bitty.broker.BrokerContainer;
import com.bitty.common.BittyContainer;
import com.bitty.common.BittyRoute;
import com.bitty.proto.Message;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class RouteBrokerContainer extends BittyContainer {

    BrokerContainer brokerContainer;

    ConcurrentHashMap<Integer, BittyRoute> routeCache = new ConcurrentHashMap<>();

    public RouteBrokerContainer(BrokerContainer brokerContainer) {
        this.brokerContainer=brokerContainer;
    }

    private void resolve(Integer bittyNodeId) {
        this.brokerContainer.getRootClient().resolve(bittyNodeId);
    }

    private BittyRoute resolveByTargetId(Integer bittyNodeId) {

        return null;
    }

    public boolean hasTarget(int targetId) {
        return false;
    }


    public void forwordRemoteMessage(Message.MessageFrame messageFrame) {
        //0 为不知道对面target在哪个节点
        if (messageFrame.getTargetNode() == 0) {
            resolve(messageFrame.getTargetId());
        }
    }

    public void push(Integer targetNodeId, BittyRouteImpl bittyRoute) {
        routeCache.put(targetNodeId,bittyRoute);
    }
}
