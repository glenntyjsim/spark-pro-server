package com.example.login.repository;

import com.example.login.model.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCleanerIdAndStatus(Long cleanerId, String status);
    List<Booking> findByUserId(Long userId);
    List<Booking> findByCleanerId(Long cleanerId);
}