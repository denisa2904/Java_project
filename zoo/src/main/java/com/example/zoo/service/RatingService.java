package com.example.zoo.service;

import com.example.zoo.model.Rating;
import com.example.zoo.model.RatingResponse;
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

    public RatingResponse getAverageRating(UUID animalId) {
        List<Rating> ratings = ratingRepo.findAllByAnimalId(animalId);
        double sum = 0;
        for(Rating r : ratings) {
            sum += r.getValue();
        }
        if(ratings.size()==0)
            return new RatingResponse(0, 0);
        return new RatingResponse((float) (sum / ratings.size()), ratings.size());
    }

    public int getRatingByAnimalIdAndUserId(UUID animalId, UUID userId) {
        if(ratingRepo.findRatingByAnimalIdAndUserId(animalId, userId).isPresent()){
            return ratingRepo.findRatingByAnimalIdAndUserId(animalId, userId).get().getValue();
        }
        return 0;
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
