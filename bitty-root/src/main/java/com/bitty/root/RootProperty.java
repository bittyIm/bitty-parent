package com.bitty.root;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class RootProperty {
    String rootIp;
    Integer rootPort;

    public RootProperty(){

    }
    public RootProperty(String s) throws IOException {
       this.loadProperty(s);
    }

    public void loadProperty(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        this.rootIp=properties.getProperty("app.root.server");
        this.rootPort=Integer.parseInt(properties.getProperty("app.root.port"));
    }

    public void loadProperty(String path) throws IOException {
        loadProperty( this.getClass().getResourceAsStream(path));
    }
}
