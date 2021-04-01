package com.bitty.root;

import com.bitty.common.BittyContainer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Data
public class Container  extends BittyContainer {

    /**
     * system config
     */
    public final Properties properties = new Properties();

    public void init() throws IOException {
        final InputStream stream = this.getClass().getResourceAsStream("/application.properties");
        properties.load(stream);
    }
}
