package com.example.login.controller;

import com.example.login.model.Review;
import com.example.login.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository repository;

    @Autowired
    public ReviewController(ReviewRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/create-review")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return ResponseEntity.ok(repository.save(review));
    }

    @GetMapping("/get-all-reviews")
    public List<Review> getAllReviews() {
        return repository.findAll();
    }

    @GetMapping("/get-review/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Integer id) {
        Optional<Review> review = repository.findById(id);
        return review.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}