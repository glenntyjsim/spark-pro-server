// --- BookingController.java ---
package com.example.login.controller;

import com.example.login.model.Booking;
import com.example.login.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;  // Import Optional

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
}
