package com.bitty.broker;

import com.bitty.broker.client.Client;
import com.bitty.broker.msg.BrokerMsgContainer;
import com.bitty.broker.route.RouteBrokerContainer;
import com.bitty.broker.user.UserBrokerContainer;
import com.bitty.common.BittyContainer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BrokerContainer extends BittyContainer {

    Client client=null;
    /**
     * 路由容器
     */
    RouteBrokerContainer routeContainer = new RouteBrokerContainer();
    /**
     * 用户容器
     */
    UserBrokerContainer userContainer = new UserBrokerContainer();
    /**
     * 消息容器
     */
    BrokerMsgContainer msgContainer = new BrokerMsgContainer();

    public void signin() {
        log.info("root server " + getProperties().get("app.root.server"));
        log.info("root port " + getProperties().get("app.root.port"));
        log.info("注册到root服务器");
        client=new Client(getProperties());
    }
}
