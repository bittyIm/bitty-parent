package com.bitty.device;

import com.bitty.common.BittyContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Container extends BittyContainer {
    /**
     * system config
     */
    public final Properties properties = new Properties();

    public void init() throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/application.properties");
        properties.load(stream);
    }
}
