package com.example.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.login.service.EmailService;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/message")
    public ResponseEntity<?> receiveContactMessage(@RequestBody Map<String, String> payload) {
        try {
            String name = payload.get("name");
            String email = payload.get("email");
            String message = payload.get("message");

            // Send email to admin or support team
            emailService.sendContactEmail(name, email, message);

            return ResponseEntity.ok("Message received and email sent.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to process message.");
        }
    }
}
