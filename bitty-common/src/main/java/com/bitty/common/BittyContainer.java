package com.bitty.common;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class BittyContainer {
    /**
     * system config
     */
    public final Properties properties = new Properties();

    public void init() throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/application.properties");
        properties.load(stream);
    }
}
