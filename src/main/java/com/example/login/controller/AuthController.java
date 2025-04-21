package com.example.login.controller;

import com.example.login.model.User;
import com.example.login.model.VerificationToken;
import com.example.login.model.ResetPassword;
import com.example.login.repository.UserRepository;
import com.example.login.repository.VerificationTokenRepository;
import com.example.login.service.EmailService;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                        VerificationTokenRepository tokenRepository,
                        EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }

        newUser.setEnabled(false);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        User savedUser = userRepository.save(newUser);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, savedUser, LocalDateTime.now().plusMinutes(30));
        tokenRepository.save(verificationToken);

        emailService.sendVerificationEmail(savedUser, token, "https://spark-pro-main.onrender.com");
        // emailService.sendVerificationEmail(savedUser, token, "https://localhost:8080");

        return ResponseEntity.ok("Please check your email to verify your account.");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) return ResponseEntity.badRequest().body("Invalid verification link.");

        VerificationToken verificationToken = optionalToken.get();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().body("Token expired.");

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);

        return ResponseEntity.ok("Email verified successfully!");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        if (found.isPresent()) {
            User existingUser = found.get();
            if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
            if (!existingUser.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please verify your email first.");
            }
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody User user) {
        Optional<User> foundOpt = userRepository.findByEmail(user.getEmail());

        if (foundOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not registered.");
        }

        User foundUser = foundOpt.get();

        String newPassword = generateRandomPassword(10);
        String encodedPassword = passwordEncoder.encode(newPassword);
        foundUser.setPassword(encodedPassword);
        userRepository.save(foundUser);

        emailService.sendNewPasswordEmail(foundUser, newPassword);

        return ResponseEntity.ok("A new password has been sent to your email.");
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

}
