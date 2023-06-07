package com.example.zoo.service;

import com.example.zoo.model.Rating;
import com.example.zoo.repository.RatingRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    public int addRating(Rating rating) {
        ratingRepo.save(rating);
        return 1;
    }

    public float getAverageRating(UUID animalId) {
        List<Rating> ratings = ratingRepo.findAllByAnimalId(animalId);
        double sum = 0;
        for(Rating r : ratings) {
            sum += r.getValue();
        }
        return (float) (sum / ratings.size());
    }

    public int getRatingByAnimalIdAndUserId(UUID animalId, UUID userId) {
        return ratingRepo.findRatingByAnimalIdAndUserId(animalId, userId).get().getValue();
    }

    public int deleteRating(UUID animalId, UUID userId) {
        Optional<Rating> r = ratingRepo.findRatingByAnimalIdAndUserId(animalId, userId);
        if(r.isPresent()) {
            ratingRepo.delete(r.get());
            return 1;
        }
        return 0;
    }

    public int updateRating(Rating rating) {
        Optional<Rating> r = ratingRepo.findRatingByAnimalIdAndUserId(rating.getAnimal().getId(), rating.getUser().getId());
        if(r.isPresent()) {
            r.get().setValue(rating.getValue());
            ratingRepo.save(r.get());
            return 1;
        }
        return 0;
    }
}
