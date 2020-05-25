package com.tucklets.app.controllers;

import com.google.gson.Gson;
import com.tucklets.app.utils.ContainerUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocaleController {

    private static final Gson GSON = new Gson();

    @GetMapping("/locale")
    public String loadLocales() {
        return GSON.toJson(ContainerUtils.createLocaleContainer());
    }
}
