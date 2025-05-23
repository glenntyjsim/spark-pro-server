package com.example.login.controller;

import com.example.login.model.Booking;
import com.example.login.model.User;
import com.example.login.model.Service;
import com.example.login.repository.BookingRepository;
import com.example.login.repository.ServiceRepository;
import com.example.login.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public BookingController(BookingRepository bookingRepo, UserRepository userRepo, ServiceRepository serviceRepo) {
        this.bookingRepository = bookingRepo;
        this.userRepository = userRepo;
        this.serviceRepository = serviceRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        booking.setUser(user);

        User cleaner = userRepository.findById(booking.getCleaner().getId())
                .orElseThrow(() -> new RuntimeException("Cleaner not found"));
        booking.setCleaner(cleaner);

        Service service = serviceRepository.findById(booking.getService().getId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        booking.setService(service);
        
        service.setStatus("booked");
        serviceRepository.save(service);
        booking.setService(service);

        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String, Object> body) {
        Long bookingId = Long.valueOf(body.get("bookingId").toString());
        String status = body.get("status").toString();

        var optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setStatus(status);
            bookingRepository.save(booking);
            return ResponseEntity.ok("Booking status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        }
    }

    @GetMapping("/pending")
    public List<Booking> getPendingByCleaner(@RequestParam("cleanerId") Long cleanerId) {
        return bookingRepository.findByCleanerIdAndStatus(cleanerId, "pending");
    }

    @GetMapping("/history/user")
    public List<Booking> getHistoryByUser(@RequestParam("userId") Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @GetMapping("/history/cleaner")
    public List<Booking> getHistoryByCleaner(@RequestParam("cleanerId") Long cleanerId) {
        return bookingRepository.findByCleanerId(cleanerId);
    }
}