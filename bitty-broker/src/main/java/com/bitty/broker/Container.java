package com.bitty.broker;

import com.bitty.broker.msg.BrokerMsgContainer;
import com.bitty.broker.route.RouteContainer;
import com.bitty.broker.user.UserContainer;
import com.bitty.common.BittyContainer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class Container extends BittyContainer {
    /**
     * 路由容器
     */
    RouteContainer routeContainer = new RouteContainer();
    /**
     * 用户容器
     */
    UserContainer userContainer = new UserContainer();
    /**
     * 消息容器
     */
    BrokerMsgContainer msgContainer = new BrokerMsgContainer();
}
