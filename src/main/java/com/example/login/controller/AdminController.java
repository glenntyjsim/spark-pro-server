package com.example.login.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.login.model.User;
import com.example.login.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;

     public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> register(@RequestBody User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }

        newUser.setEnabled(true);
        newUser.setStatus(true);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole("Admin");
        userRepository.save(newUser);
        
        return ResponseEntity.ok("Account created.");
    }

    @PostMapping("/disable-user")
    public ResponseEntity<?> disableUser(@RequestBody Map<String, Object> payload) {
        try {
            Long userId = Long.valueOf(payload.get("userId").toString());
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setStatus(false);
                userRepository.save(user);
                return ResponseEntity.ok("User disabled successfully.");
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while disabling the user.");
        }
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUserDetail(@RequestBody Map<String, Object> payload) {
        try {
            Long userId = Long.parseLong(payload.get("id").toString());
            String email = payload.get("email").toString();
            String name = payload.get("name").toString();
            String phone = payload.get("phone").toString();
            String address = payload.get("address").toString();
            boolean status = Boolean.parseBoolean(payload.get("status").toString());

            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAddress(address);
                user.setStatus(status);
                userRepository.save(user);

                return ResponseEntity.ok("User updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the user.");
        }
    }
}
