package com.bitty.broker.user;

import com.bitty.broker.Container;
import com.bitty.common.BittyUser;

import java.util.concurrent.ConcurrentHashMap;

public class UserContainer extends Container {

    ConcurrentHashMap<Integer, BittyUser> userCache = new ConcurrentHashMap<>();

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
}
