package com.nif.rest.test;

import com.nif.rest.test.config.NifiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({NifiProperties.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class NifiRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NifiRestApiApplication.class, args);
    }

}
