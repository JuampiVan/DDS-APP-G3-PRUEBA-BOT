package org.example.ddsapptelegrambot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean // <-- Esta anotación registra el objeto como un "Bean"
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}