package com.bitty.broker.user;


import com.bitty.broker.BrokerContainer;
import com.bitty.common.BittyContainer;
import com.bitty.common.BittyUser;
import com.bitty.proto.Message;

import java.util.concurrent.ConcurrentHashMap;

public class UserBrokerContainer extends BittyContainer {

    private final BrokerContainer brokerContainer;

    ConcurrentHashMap<Integer, BittyUser> userCache = new ConcurrentHashMap<>();

    public UserBrokerContainer(BrokerContainer brokerContainer) {
        this.brokerContainer=brokerContainer;
    }

    BittyUser getUser(Integer userId) {
        BittyUser user = userCache.get(userId);
        if (user == null) {
            user = resolve(userId);
        }
        return user;
    }

    private BittyUser resolve(Integer userId) {
        return null;
    }

    public void forwordLocalMessage(Message.MessageFrame messageFrame) {

    }
}
