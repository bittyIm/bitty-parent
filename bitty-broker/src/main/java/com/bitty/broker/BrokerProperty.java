package com.bitty.broker;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class BrokerProperty {
    String rootIp;
    Integer rootPort;
    String serverIp;
    String networkIp;
    Integer serverPort;
    Integer nodeId;
    Integer ttl;
    String handler;

    public BrokerProperty() {

    }

    public BrokerProperty(String s) throws IOException {
        this.loadProperty(s);
    }

    public void loadProperty(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        this.rootIp = properties.getProperty("app.root.server");
        this.rootPort = Integer.parseInt(properties.getProperty("app.root.port"));
        this.serverIp = properties.getProperty("app.broker.server");
        this.serverPort = Integer.parseInt(properties.getProperty("app.broker.port","30200"));
        this.nodeId = Integer.parseInt(properties.getProperty("app.broker.nodeId","0"));
        this.ttl = Integer.parseInt(properties.getProperty("app.broker.ttl","86400"));
        this.networkIp = properties.getProperty("app.broker.networkIp","127.0.0.1");
        this.handler =properties.getProperty("app.broker.handler","com.bitty.handler");
    }

    public void loadProperty(String path) throws IOException {
        loadProperty(this.getClass().getResourceAsStream(path));
    }
}
