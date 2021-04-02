package com.bitty.common;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class BittyContainer {

    public final Properties properties = new Properties();

    public Properties getProperties(){
        return  properties;
    }

    public void initProperty() throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/application.properties");
        properties.load(stream);
    }
}
