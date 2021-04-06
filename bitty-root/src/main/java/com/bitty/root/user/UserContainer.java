package com.bitty.root.user;


import com.bitty.common.BittyContainer;
import com.bitty.common.BittyUser;

import java.util.concurrent.ConcurrentHashMap;

public class UserContainer extends BittyContainer {

    /**
     * 所有用户的注册树
     */
    ConcurrentHashMap<Integer, BittyUser> user = new ConcurrentHashMap<>();

}
