package com.vas.travelapp.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SocketUtils;

@Configuration
@Log4j2
public class CustomPortConfiguration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${server.port}")
    private String initialServerPort;

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        int minPort = 1;
        int maxPort = 65000;
        int port = SocketUtils.findAvailableTcpPort(minPort, maxPort);

        if(initialServerPort.equals("0")) {
            factory.setPort(port);
            System.getProperties().put("server.port", port);
        }
    }
}