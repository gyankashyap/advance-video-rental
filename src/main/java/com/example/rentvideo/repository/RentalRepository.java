package com.example.rentvideo.repository;

import com.example.rentvideo.model.Rental;
import com.example.rentvideo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndActiveTrue(User user);
    Optional<Rental> findByVideoIdAndUserIdAndActiveTrue(Long videoId, Long userId);
    int countByUserIdAndActiveTrue(Long userId);
}
