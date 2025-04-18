package com.example.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class LoginApplication {
    public static void main(String[] args) {
        // Load .env
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing() // for deployment (Render)
                .load();

        // Map .env values to System properties Spring Boot expects
        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(LoginApplication.class, args);
    }
}
