package com.example.login.controller;

import com.example.login.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final String HARD_CODED_USERNAME = "user";
    private final String HARD_CODED_PASSWORD = "password";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User requestUser) {
        if (HARD_CODED_USERNAME.equals(requestUser.getUsername()) && HARD_CODED_PASSWORD.equals(requestUser.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }
}
