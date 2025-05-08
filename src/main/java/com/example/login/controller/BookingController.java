package com.example.login.controller;

import com.example.login.model.Booking;
import com.example.login.model.Service;
import com.example.login.repository.BookingRepository;
import com.example.login.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        Map<String, Object> response = new HashMap<>();
        response.put("booking_id", savedBooking.getBookingId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-status/{bookingId}")
    public ResponseEntity<String> updateBookingStatus(@PathVariable Long bookingId, @RequestBody Map<String, String> payload) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Booking not found.");
        }

        Booking booking = bookingOptional.get();
        String newStatus = payload.get("status");

        // Update the status
        booking.setStatus(newStatus);
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking status updated successfully.");
    }

    @GetMapping("/get-pending-bookings/{cleanerId}")
    public ResponseEntity<List<Map<String, Object>>> getPendingBookingsByCleanerId(@PathVariable Long cleanerId) {
        // Retrieve all bookings with status 'pending' for the given cleanerId
        List<Booking> bookings = bookingRepository.findByCleanerIdAndStatus(cleanerId, "pending");

        // Map bookings to a response containing booking and service details
        List<Map<String, Object>> response = bookings.stream().map(booking -> {
            Service service = booking.getService(); // Assuming Booking has a Service object

            Map<String, Object> bookingDetails = Map.of(
                "bookingId", booking.getBookingId(),
                "name", booking.getName(),
                "email", booking.getEmail(),
                "phone", booking.getPhone(),
                "address", booking.getAddress(),
                "note", booking.getNote(),
                "duration", booking.getDuration(),
                "status", booking.getStatus(),
                "serviceType", service.getServices(),
                "serviceDate", service.getDate(),
                "timeFrom", service.getTimeFrom(),
                "timeTo", service.getTimeTo()
            );
            return bookingDetails;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
