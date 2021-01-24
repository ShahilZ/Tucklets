package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final String baseUrl;
    private final String presidentEmail;

    /* These are the variables stored in application.properties*/
    public AppConfig(
            @Value("${tucklets.base.url}") String baseUrl,
            @Value("${tucklets.president.email}") String presidentEmail)
    {
        this.baseUrl = baseUrl;
        this.presidentEmail = presidentEmail;
    }

    public String getTuckletsBaseUrl() {
        return baseUrl;
    }

    public String getPresidentEmail() {
        return presidentEmail;
    }
}