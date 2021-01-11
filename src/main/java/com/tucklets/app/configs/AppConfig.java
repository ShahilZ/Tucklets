package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private final String tuckletsBaseUrl;

    /* These are the variables stored in application.properties*/
    public AppConfig(@Value("${tucklets.base.url}") String tuckletsBaseUrl)
    {
        this.tuckletsBaseUrl = tuckletsBaseUrl;
    }

    public String getTuckletsBaseUrl() {
        return tuckletsBaseUrl;
    }
}