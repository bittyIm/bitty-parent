package com.bitty.common;

import com.bitty.common.payload.Payload;

/**
 * bitty网络传输的消息
 */
public interface BittyMsg {
    /**
     * 消息目标id
     * @return
     */
    Long getTargetId();

    /**
     * 消息原始id
     * @return
     */
    Long getSourceId();

    /**
     * 消息目标节点
     * @return
     */
    Integer getTargetBitty();

    /**消息来源节点
     *
     * @return
     */
    Integer getSourceBitty();

    /**
     * 获取消息id
     *
     * @return
     */
    Integer getMid();

    /**
     * 消息内容
     */
    Payload getPayload();
}
