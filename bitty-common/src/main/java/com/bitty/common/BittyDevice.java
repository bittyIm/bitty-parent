package com.bitty.common;

public interface BittyDevice {
    /**
     * 消息 游标
     */
    Integer getOffset();

    /**
     * 同步消息
     * @return
     */
    Integer aync();
}
