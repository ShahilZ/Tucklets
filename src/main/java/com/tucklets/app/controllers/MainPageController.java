package com.tucklets.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
public class MainPageController {

    @Autowired
    LocaleResolver localeResolver;

    @GetMapping("/test")
    public Map<String, String> test(HttpServletRequest request) {
        Map<String, String> testResponse = new HashMap<>();
        var text = ResourceBundle.getBundle("i18n.messages", localeResolver.resolveLocale(request));
        var name = text.getString("sponsor-info.subtitle");
        testResponse.put("name", name);
        return testResponse;
    }

    @GetMapping("/fetchConfigs")
    public Map<String, String> fetchConfigs() {
        Map<String, String> configsResponse = new HashMap<>();
        configsResponse.put("paypal_client_id", System.getenv("PAYPAL_CLIENT_ID"));
        return configsResponse;
    }
}
