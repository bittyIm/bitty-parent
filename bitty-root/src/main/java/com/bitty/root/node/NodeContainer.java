package com.bitty.root.node;

import com.bitty.common.BittyContainer;
import com.bitty.common.BittyUser;

import java.util.concurrent.ConcurrentHashMap;

public class NodeContainer extends BittyContainer {
    /**
     * 所有节点的注册树
     */
    ConcurrentHashMap<Integer, BittyUser> nodeList = new ConcurrentHashMap<>();

}
