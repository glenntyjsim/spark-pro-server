package com.example.login.controller;

import com.example.login.model.Notification;
import com.example.login.model.Service;
import com.example.login.model.User;
import com.example.login.repository.NotificationRepository;
import com.example.login.repository.ServiceRepository;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/create-notification")
    public ResponseEntity<?> create(@RequestParam Long userId,
                                    @RequestParam Long cleanerId,
                                    @RequestParam Long serviceId,
                                    @RequestParam String title,
                                    @RequestParam String notification,
                                    @RequestParam(required = false) String cleanerProfile) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            User cleaner = userRepository.findById(cleanerId).orElse(null);
            Service service = serviceRepository.findById(serviceId).orElse(null);

            Notification n = new Notification();
            n.setUser(user);
            n.setCleaner(cleaner);
            n.setService(service);
            n.setTitle(title);
            n.setNotification(notification);
            n.setCleanerProfile(cleanerProfile);
            n.setCreatedDate(LocalDate.now());
            n.setIsRead(false);

            notificationRepository.save(n);
            return ResponseEntity.ok("Notification created successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating notification: " + e.getMessage());
        }
    }

    @PutMapping("/read-notification")
    public ResponseEntity<?> updateRead(@RequestParam Long notificationId,
                                        @RequestParam boolean isRead) {
        try {
            Notification n = notificationRepository.findById(notificationId).orElseThrow();
            n.setIsRead(isRead);
            notificationRepository.save(n);
            return ResponseEntity.ok("Notification updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating notification: " + e.getMessage());
        }
    }

    @GetMapping("/user-notification")
    public ResponseEntity<?> getByUser(@RequestParam Long userId) {
        try {
            List<Notification> list = notificationRepository.findByUserId(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving notifications: " + e.getMessage());
        }
    }

    @GetMapping("/notification-detail")
    public ResponseEntity<?> getDetail(@RequestParam Long notificationId) {
        try {
            Notification n = notificationRepository.findById(notificationId).orElseThrow();
            return ResponseEntity.ok(n);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving notification detail: " + e.getMessage());
        }
    }
}
