package com.tucklets.app.configs;

import com.braintreegateway.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final String baseUrl;
    private final String presidentEmail;
    private final String brainTreeEnvironment;

    /* These are the variables stored in application.properties*/
    public AppConfig(
            @Value("${tucklets.base.url}") String baseUrl,
            @Value("${tucklets.president.email}") String presidentEmail,
            @Value("${brainTree.environment}") String brainTreeEnvironment)
    {
        this.baseUrl = baseUrl;
        this.presidentEmail = presidentEmail;
        this.brainTreeEnvironment = brainTreeEnvironment;
    }

    public String getTuckletsBaseUrl() {
        return baseUrl;
    }

    public String getPresidentEmail() {
        return presidentEmail;
    }

    public String getBrainTreeEnvironment() {
        return brainTreeEnvironment;
    }
}