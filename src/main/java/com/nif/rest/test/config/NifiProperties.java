package com.nif.rest.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nifi.api", ignoreUnknownFields = false)
@Data
public class NifiProperties {
    private Url Url;

    @Data
    public static class Url {
        private String createProcessors;
        private String createConnections;
        private String createControllerServices;
        private String createProcessGroups;
        private String processors;
        private String controllerServices;
        private String controllerServiceReferences;
    }
}