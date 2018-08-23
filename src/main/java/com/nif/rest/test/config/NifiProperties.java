package com.nif.rest.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nifi", ignoreUnknownFields = false)
@Data
public class NifiProperties {
    private Base base;

    private DriverLocation driverLocation;

    private ApiUrl apiUrl;

    @Data
    public static class Base {
        private String url;
    }

    @Data
    public static class DriverLocation {
        private String mysql;

        public String get(String driverType) {
            switch (driverType) {
                case "mysql":
                    return mysql;
            }
            return null;
        }
    }

    @Data
    public static class ApiUrl {
        private String createProcessors;
        private String createConnections;
        private String createControllerServices;
        private String createProcessGroups;
        private String processors;
        private String controllerServices;
        private String controllerServiceReferences;
    }
}