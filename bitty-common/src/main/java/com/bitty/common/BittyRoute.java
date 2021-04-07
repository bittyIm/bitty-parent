package com.bitty.common;

import com.bitty.proto.Message;

public interface BittyRoute {
    /**
     * 转发路由
     * @param messageFrame
     */
    void forward(Message.MessageFrame messageFrame);

    Integer getTargetNodeId();
}
