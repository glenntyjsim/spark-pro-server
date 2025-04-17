package com.example.login.controller;

import com.example.login.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    // Thread-safe in-memory user store
    private static final Map<String, String> users = new ConcurrentHashMap<>();

    static {
        users.put("admin", "admin123"); // default user
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User newUser) {
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password must not be null.");
        }

        if (users.containsKey(newUser.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        users.put(newUser.getUsername(), newUser.getPassword());
        return ResponseEntity.ok("Registration successful!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User requestUser) {
        String storedPassword = users.get(requestUser.getUsername());

        if (storedPassword != null && storedPassword.equals(requestUser.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }
}
