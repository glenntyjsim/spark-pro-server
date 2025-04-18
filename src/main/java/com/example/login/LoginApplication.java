package com.example.login;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.configure()
                .directory("./") // defaults to root directory
                .ignoreIfMissing() // prevents crash in production if .env is missing
                .load();

            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        } catch (Exception e) {
            System.out.println("No .env file found or failed to load. Using system environment variables.");
        }

        SpringApplication.run(LoginApplication.class, args);
    }
}
