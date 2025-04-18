package com.example.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // allow all paths
            .allowedOrigins("http://localhost:3000") // allow frontend
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // allow these methods
            .allowedHeaders("*") // allow all headers
            .allowCredentials(true); // allow credentials (cookies, etc.)
    }
}