package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private final String domainUrl;

    /* These are the variables stored in application.properties*/
    public AppConfig(@Value("${domain.url}") String domainUrl)
    {
        this.domainUrl = domainUrl;
    }

    public String getDomainUrl() {
        return domainUrl;
    }
}