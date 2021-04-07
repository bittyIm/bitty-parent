package com.bitty.device;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class DeviceProperty {
    String serverIp;
    Integer serverPort;
    Integer nodeId;
    public DeviceProperty(){

    }
    public DeviceProperty(String s) throws IOException {
       this.loadProperty(s);
    }

    public void loadProperty(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        this.serverIp=properties.getProperty("app.broker.server");
        this.serverPort=Integer.parseInt(properties.getProperty("app.broker.port"));
    }

    public void loadProperty(String path) throws IOException {
        loadProperty( this.getClass().getResourceAsStream(path));
    }
}
