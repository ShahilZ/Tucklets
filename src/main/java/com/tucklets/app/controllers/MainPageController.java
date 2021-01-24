package com.tucklets.app.controllers;
import com.tucklets.app.configs.SecretsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainPageController {

    @Autowired
    SecretsConfig secretsConfig;

    @GetMapping("/fetchConfigs")
    public Map<String, String> fetchConfigs() {
        Map<String, String> configsResponse = new HashMap<>();
        configsResponse.put("paypal_client_id", secretsConfig.getPayPalClientId());
        return configsResponse;
    }
}
