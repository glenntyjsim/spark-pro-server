package com.example.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all paths
            .allowedOriginPatterns("*") // Allow all origins (recommended over allowedOrigins("*"))
            .allowedMethods("*") // Allow all HTTP methods (GET, POST, etc.)
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true); // Allow cookies, authorization headers, etc.
    }
}
