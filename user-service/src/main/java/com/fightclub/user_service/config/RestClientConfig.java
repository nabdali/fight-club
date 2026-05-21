package com.fightclub.user_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${services.character.url}")
    private String characterServiceUrl;

    @Bean
    public RestClient characterRestClient() {
        return RestClient.builder()
                .baseUrl(characterServiceUrl)
                .build();
    }
}
