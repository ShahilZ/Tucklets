package com.tucklets.app.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/frontend/dist/**", "/frontend/static/css/**", "/frontend/static/img/**", "/static/img/**")
            .addResourceLocations("classpath:/frontend/dist/", "file:frontend/dist/",
                    "classpath:/frontend/static/img/", "file:frontend/static/img/");
    }

}