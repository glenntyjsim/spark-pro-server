package com.example.login.repository;

import com.example.login.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findByUserId(Long userId);
    List<Favourite> findByCleanerId(Long cleanerId);
    Optional<Favourite> findByCleanerIdAndUserId(Long cleanerId, Long userId);
}
