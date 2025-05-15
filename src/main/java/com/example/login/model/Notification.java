package com.example.login.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String notification;
    private LocalDate createdDate;
    private boolean isRead = false;
    private String cleanerProfile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cleaner_id")
    private User cleaner;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getCleanerProfile() {
        return cleanerProfile;
    }

    public void setCleanerProfile(String cleanerProfile) {
        this.cleanerProfile = cleanerProfile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCleaner() {
        return cleaner;
    }

    public void setCleaner(User cleaner) {
        this.cleaner = cleaner;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
