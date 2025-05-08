package com.example.login.repository;

import com.example.login.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Add the method to find bookings by cleanerId and status
    List<Booking> findByCleanerIdAndStatus(Long cleanerId, String status);
}
