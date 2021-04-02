package com.bitty.common.msg;

import com.bitty.common.BittyContainer;
import com.bitty.common.BittyMsg;

import java.util.concurrent.ConcurrentHashMap;

public class MsgContainer extends BittyContainer {
    ConcurrentHashMap<Integer, BittyMsg> routeMsg = new ConcurrentHashMap<>();

    BittyMsg getMsg(Integer msgId) {
        BittyMsg msg = routeMsg.get(msgId);
        return msg;
    }
}
