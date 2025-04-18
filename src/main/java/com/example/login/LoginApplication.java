package com.example.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class LoginApplication {
    public static void main(String[] args) {
        // Try to load from .env (for local dev)
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // First try from environment (Render), fallback to .env (local)
        String dbUrl = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : dotenv.get("DB_URL");
        String dbUser = System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : dotenv.get("DB_USERNAME");
        String dbPass = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : dotenv.get("DB_PASSWORD");
        String smtpUser = System.getenv("SMTP_USERNAME") != null ? System.getenv("SMTP_USERNAME") : dotenv.get("SMTP_USERNAME");
        String smtpPass = System.getenv("SMTP_PASSWORD") != null ? System.getenv("SMTP_PASSWORD") : dotenv.get("SMTP_PASSWORD");

        if (dbUrl == null || dbUser == null || dbPass == null || smtpUser == null || smtpPass == null) {
            System.err.println("Missing DB credentials. Check .env or Render environment.");
            System.exit(1);
        }

        System.setProperty("spring.datasource.url", dbUrl);
        System.setProperty("spring.datasource.username", dbUser);
        System.setProperty("spring.datasource.password", dbPass);
        System.setProperty("spring.mail.username", smtpUser);
        System.setProperty("spring.mail.password", smtpPass);

        SpringApplication.run(LoginApplication.class, args);
    }
}