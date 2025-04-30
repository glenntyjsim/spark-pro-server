package com.example.login.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String bio;
    private String profile_photo;
    private Integer experience_year;
    private String preferred_region;
    private Date date_created;
    private boolean enabled;
    private String role;
    private boolean status;

    // Constructors
    public User() {}

    public User(String email, String password, String name, String phone, String address, String bio, String profile_photo, 
                Integer experience_year, String preferred_region, Date date_created, boolean enabled, String role, boolean status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.bio = bio;
        this.profile_photo = profile_photo;
        this.experience_year = experience_year;
        this.preferred_region = preferred_region;
        this.date_created = date_created;
        this.enabled = enabled;
        this.role = role;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.date_created = new Date(System.currentTimeMillis());
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePhoto() {
        return profile_photo;
    }

    public void setProfilePhoto(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public Integer getExperienceYear() {
        return experience_year;
    }

    public void setExperienceYear(Integer experience_year) {
        this.experience_year = experience_year;
    }

    public String getPreferredRegion() {
        return preferred_region;
    }

    public void setPreferredRegion(String preferred_region) {
        this.preferred_region = preferred_region;
    }

    public Date getDateCreated() {
        return date_created;
    }

    public void setDateCreated(Date date_created) {
        this.date_created = date_created;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}