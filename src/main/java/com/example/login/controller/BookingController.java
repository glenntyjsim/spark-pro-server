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
        // Fetch and set managed User
        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        booking.setUser(user);

        // Fetch and set managed Service
        Service service = serviceRepository.findById(booking.getService().getId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        booking.setService(service);

        // Save booking
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/pending")
    public List<Booking> getPendingByCleaner(@RequestParam("cleanerId") Long cleanerId) {
        return bookingRepository.findByUserIdAndStatus(cleanerId, "pending");
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

<<<<<<< HEAD
    @GetMapping("/history/cleaner")
    public List<Booking> getHistoryByCleaner(@RequestParam("cleanerId") Long cleanerId) {
        return bookingRepository.findByUserId(cleanerId);
=======
    @GetMapping("/get-pending-bookings/{cleanerId}")
    public ResponseEntity<List<Map<String, Object>>> getPendingBookingsByCleanerId(@PathVariable Long cleanerId) {
        // Retrieve all bookings with status 'pending' for the given cleanerId
        List<Booking> bookings = bookingRepository.findByCleanerIdAndStatus(cleanerId, "pending");

        // Map bookings to a response containing booking and service details
        List<Map<String, Object>> response = bookings.stream().map(booking -> {
            Service service = booking.getService(); // Assuming Booking has a Service object

            Map<String, Object> bookingDetails = new HashMap<>();
            bookingDetails.put("bookingId", booking.getBookingId());
            bookingDetails.put("name", booking.getName());
            bookingDetails.put("email", booking.getEmail());
            bookingDetails.put("phone", booking.getPhone());
            bookingDetails.put("address", booking.getAddress());
            bookingDetails.put("note", booking.getNote());
            bookingDetails.put("duration", booking.getDuration());
            bookingDetails.put("status", booking.getStatus());
            bookingDetails.put("serviceType", service.getServices());
            bookingDetails.put("serviceDate", service.getDate());
            bookingDetails.put("timeFrom", service.getTimeFrom());
            bookingDetails.put("timeTo", service.getTimeTo());
            return bookingDetails;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
>>>>>>> 5032af6a2ab6dc5ec51f17dd51f22a260aa1e79a
    }

    @GetMapping("/history/user")
    public List<Booking> getHistoryByUser(@RequestParam Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}