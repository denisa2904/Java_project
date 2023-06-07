package com.example.zoo.repository;

import com.example.zoo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepo extends JpaRepository<Image, UUID> {

    Optional<Image> findImageByAnimalId(UUID animalId);

}
