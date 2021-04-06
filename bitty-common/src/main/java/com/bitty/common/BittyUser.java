package com.bitty.common;

public interface BittyUser {
    /**
     * 获取用户id
     *
     * @return
     */
    Long getId();

    /**
     * 获取用户的全部属性
     *
     * @return
     */
    BittyAttr getAttr(String key);

    /**
     * 设置用户的一个属性
     * @param key
     */
    void setAttr(String key,Object val);

    /**
     * 获取用户的主设备
     *
     * @return
     */
    BittyDevice getMainDevice();
}
