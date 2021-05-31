package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AppConfig {

    private final String baseUrl;
    private final String presidentEmail;
    private final String brainTreeEnvironment;
    private final String transactionalEmailsFromAddress;

    /* These are the variables stored in application.properties*/
    public AppConfig(
            @Value("${tucklets.base.url}") String baseUrl,
            @Value("${tucklets.president.email}") String presidentEmail,
            @Value("${brainTree.environment}") String brainTreeEnvironment,
            @Value("${transactional.emails.from.address}") String transactionalEmailsFromAddress)
    {
        this.baseUrl = baseUrl;
        this.presidentEmail = presidentEmail;
        this.brainTreeEnvironment = brainTreeEnvironment;
        this.transactionalEmailsFromAddress = transactionalEmailsFromAddress;
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

    public String getTransactionalEmailsFromAddress() {
        return transactionalEmailsFromAddress;
    }
}