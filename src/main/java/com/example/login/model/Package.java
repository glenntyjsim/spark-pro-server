package com.example.login.model;

import jakarta.persistence.*;

@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(nullable = false)
    private String name;

    @Column
    private String feature;

    @Column(nullable = false)
    private Double price;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFeature() { return feature; }
    public void setFeature(String feature) { this.feature = feature; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
