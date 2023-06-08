package com.example.zoo.api;

import com.example.zoo.model.Rating;
import com.example.zoo.model.User;
import com.example.zoo.service.AnimalService;
import com.example.zoo.service.JwtService;
import com.example.zoo.service.RatingService;
import com.example.zoo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/animals")
public class RatingController {
    private final RatingService ratingService;
    private final AnimalService animalService;
    private final JwtService jwtService;
    private final UserService userService;

    public RatingController(RatingService ratingService, AnimalService animalService, JwtService jwtService, UserService userService) {
        this.ratingService = ratingService;
        this.animalService = animalService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("{id}/rating")
    public float getRatingByAnimalId(@PathVariable("id") UUID id) {
        return ratingService.getAverageRating(animalService.getAnimalById(id).get().getId());
    }

    @GetMapping("{id}/myRating")
    public ResponseEntity<?> getMyRating(@PathVariable("id") UUID id, @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        User user = userService.getUserByUsername(username);
        if(ratingService.getRatingByAnimalIdAndUserId(id, user.getId())==0)
            return ResponseEntity.status(404).body("Rating not found".getBytes());
        return ResponseEntity.status(200).body(ratingService.getRatingByAnimalIdAndUserId(id, user.getId()));
    }


    @PostMapping("{id}/rating")
    public ResponseEntity<?> addRating(@PathVariable("id") UUID id, @RequestBody Rating rating,
                         @NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        rating.setAnimal(animalService.getAnimalById(id).get());
        User user = userService.getUserByUsername(username);
        rating.setUser(user);
        if(ratingService.addRating(rating)==1)
            return ResponseEntity.status(201).body("Rating added successfully".getBytes());
        return ResponseEntity.status(400).body("Rating not added".getBytes());
    }

    @PutMapping("{id}/rating")
    public ResponseEntity<?> updateRating(@PathVariable("id") UUID id, @RequestBody Rating rating,
                            @NonNull HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String jwt;
        jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        User user = userService.getUserByUsername(username);
        rating.setAnimal(animalService.getAnimalById(id).get());
        rating.setUser(user);
        if(ratingService.updateRating(rating)==1)
            return ResponseEntity.status(204).body("Rating updated successfully".getBytes());
        return ResponseEntity.status(400).body("Rating not updated".getBytes());

    }
}
