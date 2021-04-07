package com.bitty.broker;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrokerPropertyTest {

    @Test
    void init() throws IOException {
        BrokerProperty brokerProperty=new BrokerProperty();
        brokerProperty.loadProperty(Broker.class.getResourceAsStream("/application.properties"));
        assertEquals(brokerProperty.getNodeId(),0);
    }
}