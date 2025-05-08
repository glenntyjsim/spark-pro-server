// --- BookingRepository.java ---
package com.example.login.repository;

import com.example.login.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}