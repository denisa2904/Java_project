package com.example.zoo.repository;

import com.example.zoo.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingRepo extends JpaRepository<Rating, UUID> {

    Optional<Rating> findRatingByAnimalIdAndUserId(UUID animalId, UUID userId);
    List<Rating> findAllByAnimalId(UUID animalId);
}
