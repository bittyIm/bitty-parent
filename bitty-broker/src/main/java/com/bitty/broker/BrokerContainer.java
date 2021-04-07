package com.bitty.broker;

import com.bitty.broker.client.RootClient;
import com.bitty.broker.msg.BrokerMsgContainer;
import com.bitty.broker.route.BittyRouteImpl;
import com.bitty.broker.route.RouteBrokerContainer;
import com.bitty.broker.user.UserBrokerContainer;
import com.bitty.common.BittyContainer;
import com.bitty.proto.Message;
import io.netty.util.HashedWheelTimer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BrokerContainer extends BittyContainer {


    /**
     * 异常连接处理器
     */
    HashedWheelTimer timer = new HashedWheelTimer();


    RootClient rootClient = null;
    /**
     * 路由容器
     */
    RouteBrokerContainer routeContainer ;

    /**
     * 用户容器
     */
    UserBrokerContainer userContainer;


    BrokerContainer(){
        this.routeContainer=new RouteBrokerContainer(this);
        this.userContainer=new UserBrokerContainer(this);
    }
    /**
     * 消息容器
     */
    BrokerMsgContainer msgContainer = new BrokerMsgContainer();

    public void signin(Broker broker) {
        rootClient = new RootClient(broker);
    }

    public void forwordRemoteMessage(Message.MessageFrame messageFrame) {
        routeContainer.forwordRemoteMessage(messageFrame);
    }

    public void forwordLocalMessage(Message.MessageFrame messageFrame) {
        userContainer.forwordLocalMessage(messageFrame);
    }

    public void forwordMessage(Message.MessageFrame messageFrame) {
//        if(messageFrame.getTargetNode()==(int)(this.routeContainer.properties.get("app.broker.nodeId"))){
//            forwordLocalMessage(messageFrame);
//        }else {
//            forwordRemoteMessage(messageFrame);
//        }
    }

    public void initNodeForward(){

    }
    /**
     * 找到了节点
     * @param msg
     */
    public void resolveNodeCallback(com.bitty.proto.Broker.resolveNode msg) {
        var bittyRoute=new BittyRouteImpl(msg,this);
        routeContainer.push(bittyRoute.getTargetNodeId(),bittyRoute);
    }
}
