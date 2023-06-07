package com.example.zoo.repository;

import com.example.zoo.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalRepo extends JpaRepository<Animal, UUID> {

    Optional<Animal> findAnimalByName(String name);

    List<Animal> findAllByNameContaining(String name);
    List<Animal> findAllByTypeContaining(String type);
    List<Animal> findAllByBinomialNameContaining(String binomialName);
    List<Animal> findAllByDescriptionContaining(String description);
    List<Animal> findAllByClimateContaining(String climate);
    List<Animal> findAllByConservationContaining(String conservation);
    List<Animal> findAllByOriginContaining(String origin);
    List<Animal> findAllByType(String type);
    List<Animal> findAllByClimate(String climate);
    List<Animal> findAllByConservation(String conservation);
    List<Animal> findAllByOrigin(String origin);
}
