package com.example.login.repository;

import com.example.login.model.Service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByUserId(Long userId);
}
