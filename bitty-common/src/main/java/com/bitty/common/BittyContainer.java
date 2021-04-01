package com.bitty.common;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
@Slf4j
public class BittyContainer {
    /**
     * system config
     */
    public final Properties properties = new Properties();

    public void initProperty() throws IOException {
        log.info("ROOT 配置初始化");
        final InputStream stream = this.getClass().getResourceAsStream("/application.properties");
        properties.load(stream);
    }
}
