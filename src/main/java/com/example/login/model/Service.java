package com.example.login.model;

import jakarta.persistence.*;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String services; // comma-separated string e.g., "Cleaning,Laundry"

    @Column(nullable = false)
    private String date;

    @Column(name = "time_from", nullable = false)
    private String timeFrom;

    @Column(name = "time_to", nullable = false)
    private String timeTo;

    @Column(nullable = false)
    private double price;

    private String status = "pending";

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setuserId(Long userId) { this.userId = userId; }

    public String getServices() { return services; }
    public void setServices(String services) { this.services = services; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTimeFrom() { return timeFrom; }
    public void setTimeFrom(String timeFrom) { this.timeFrom = timeFrom; }

    public String getTimeTo() { return timeTo; }
    public void setTimeTo(String timeTo) { this.timeTo = timeTo; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
