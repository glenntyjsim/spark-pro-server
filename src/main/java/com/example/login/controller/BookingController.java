// --- BookingController.java ---
package com.example.login.controller;

import com.example.login.model.Booking;
import com.example.login.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        Map<String, Object> response = new HashMap<>();
        response.put("booking_id", savedBooking.getBookingId());
        return ResponseEntity.ok(response);
    }
}