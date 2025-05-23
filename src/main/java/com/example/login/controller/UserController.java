package com.example.login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.login.model.User;
import com.example.login.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;

     public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", user.getName());
            userData.put("email", user.getEmail());
            userData.put("phone", user.getPhone());
            userData.put("address", user.getAddress());
            userData.put("bio", user.getBio());
            userData.put("profile_photo", user.getProfilePhoto());
            userData.put("experience_year", user.getExperienceYear());
            userData.put("preferred_region", user.getPreferredRegion());
            return ResponseEntity.ok(userData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserDetail(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email"); // identify user by email
        String name = (String) payload.get("name");
        String phone = (String) payload.get("phone");
        String address = (String) payload.get("address");
        String bio = (String) payload.get("bio");
        String profilePhoto = (String) payload.get("profilePhoto");
        Integer experienceYear = (Integer) payload.get("experienceYear");
        String preferredRegion = (String) payload.get("preferredRegion");

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(name);
            user.setPhone(phone);
            user.setAddress(address);
            user.setBio(bio);
            user.setProfilePhoto(profilePhoto);
            user.setExperienceYear(experienceYear);
            user.setPreferredRegion(preferredRegion);
            userRepository.save(user);

            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> passwordMap) {
        String email = passwordMap.get("email"); // You can get this from JWT or request if needed
        String currentPassword = passwordMap.get("currentPassword");
        String newPassword = passwordMap.get("newPassword");

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        // Compare current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }
}
