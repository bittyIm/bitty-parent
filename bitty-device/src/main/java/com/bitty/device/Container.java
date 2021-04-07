package com.bitty.device;

import com.bitty.common.BittyContainer;
import com.bitty.common.Network;
import com.bitty.device.local.Client;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Data
@Slf4j
public class Container<T> extends BittyContainer {

    DeviceProperty property=null;

    Client client=null;

    Network network;
    /**
     * 容器同步游标
     */
    Long offset;

    Map<T, Ap> accessPointList = new HashMap<T, Ap>();

    /**
     * 同步用户列表
     */
    void syncAccessPointList(){
        log.info("本地缓存恢复");
    }

    void setNetWork(Network netWork){
        this.network=netWork;
    }

    Network getNetwork(){
        return null;
    }

    protected void initLocalClient() {
        log.info("初始化服务器连接");
        client=new Client(property);
    }

    public void init(DeviceProperty deviceProperty){
        property=deviceProperty;
        initLocalClient();
//        initLocalNetwork();
        syncAccessPointList();
    }
}
