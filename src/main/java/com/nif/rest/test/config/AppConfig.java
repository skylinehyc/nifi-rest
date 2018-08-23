package com.nif.rest.test.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .dateFormat(new SimpleDateFormat("HH:mm:ss zzz"))
                .serializationInclusion(Include.NON_NULL);
    }

}
