package com.example.zoo.api;

import com.example.zoo.model.Rating;
import com.example.zoo.service.AnimalService;
import com.example.zoo.service.RatingService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/animals")
public class RatingController {
    private final RatingService ratingService;
    private final AnimalService animalService;

    public RatingController(RatingService ratingService, AnimalService animalService) {
        this.ratingService = ratingService;
        this.animalService = animalService;
    }

    @GetMapping("{id}/rating")
    public float getRatingByAnimalId(@PathVariable("id") UUID id) {
        return ratingService.getAverageRating(animalService.getAnimalById(id).get().getId());
    }

    /*
    @GetMapping("{id}/myRating")
     get my Rating
    */

    @PostMapping("{id}/rating")
    public int addRating(@PathVariable("id") UUID id, @RequestBody int value) {
        Rating rating = new Rating();
        rating.setAnimal(animalService.getAnimalById(id).get());
        rating.setValue(value);
        //rating.setUser(null);
        return ratingService.addRating(rating);
    }

    @PutMapping("{id}/rating")
    public int updateRating(@PathVariable("id") UUID id, @RequestBody int value) {
        Rating rating = new Rating();
        rating.setAnimal(animalService.getAnimalById(id).get());
        rating.setValue(value);
        //rating.setUser(null);
        return ratingService.updateRating(rating);
    }
}
