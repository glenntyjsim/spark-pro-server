package com.example.login.controller;

import com.example.login.model.Favourite;
import com.example.login.model.User;
import com.example.login.repository.FavouriteRepository;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {

    @Autowired
    private FavouriteRepository favouriteRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/add-favourite")
    public ResponseEntity<String> addToFavourites(@RequestBody Map<String, Long> request) {
        Long cleanerId = request.get("cleanerId");
        Long userId = request.get("userId");

        Optional<User> cleanerOpt = userRepo.findById(cleanerId);
        Optional<User> userOpt = userRepo.findById(userId);

        if (cleanerOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid cleaner or user ID.");
        }

        Optional<Favourite> existing = favouriteRepo.findByCleanerIdAndUserId(cleanerId, userId);
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("This cleaner is already in your favourites.");
        }

        Favourite fav = new Favourite();
        fav.setCleaner(cleanerOpt.get());
        fav.setUser(userOpt.get());
        fav.setDateCreated(new Date(System.currentTimeMillis()));

        favouriteRepo.save(fav);
        return ResponseEntity.ok("Cleaner added to favourites.");
    }

    @GetMapping("/get-favourite")
    public ResponseEntity<?> getFavouriteCleaners(@RequestParam Long userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user ID.");
        }

        List<Favourite> favourites = favouriteRepo.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Favourite fav : favourites) {
            User cleaner = fav.getCleaner();
            Map<String, Object> cleanerInfo = new HashMap<>();
            cleanerInfo.put("favouriteId", fav.getId());
            cleanerInfo.put("cleanerId", cleaner.getId());
            cleanerInfo.put("name", cleaner.getName());
            cleanerInfo.put("email", cleaner.getEmail());   
            cleanerInfo.put("dateCreated", fav.getDateCreated());
            result.add(cleanerInfo);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/remove-favourite")
    public ResponseEntity<String> removeFromFavourites(@RequestParam Long favouriteId) {
        if (!favouriteRepo.existsById(favouriteId)) {
            return ResponseEntity.badRequest().body("Favourite not found.");
        }

        favouriteRepo.deleteById(favouriteId);
        return ResponseEntity.ok("Favourite removed successfully.");
    }
}