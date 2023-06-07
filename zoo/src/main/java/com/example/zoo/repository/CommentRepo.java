package com.example.zoo.repository;

import com.example.zoo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByAnimalIdAndUserId(UUID animalId, UUID userId);
    List<Comment> findAllByAnimalId(UUID animalId);
}
