package com.example.login.controller;

import com.example.login.model.Service;
import com.example.login.repository.ServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    
    @Autowired
    private final ServiceRepository repository;

    public ServiceController(ServiceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/get-service")
    public List<Service> getAllServices() {
        return repository.findAll();
    }

    @GetMapping("/get-service/{userId}")
    public List<Service> getServiceByUserId(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }

    @PostMapping("/add-service")
    public ResponseEntity<Service> addService(@RequestBody Service record) {
        Service saved = repository.save(record);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update-service/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service record) {
        Optional<Service> existing = repository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        record.setId(id);
        return ResponseEntity.ok(repository.save(record));
    }

    @DeleteMapping("/remove-service/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
