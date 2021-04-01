package com.bitty.device;

public interface Ap {

    void setOffset(Long mid);
    /**
     *
     */
    void sync();

    /**
     * get user attr
     *
     * @param name
     */
    void getAttr(String name);

    /**
     * set user attr
     *
     * @param name
     * @param val
     */
    void setAttr(String name, Object val);

    /**
     * @param msg
     */
    void sendMsg(Msg msg);

    /**
     * set message callback
     *
     * @param f
     */
    void sendMsgCallBack(Func f);

    /**
     * get network
     * @return
     */
    Network getNetwork();
}
