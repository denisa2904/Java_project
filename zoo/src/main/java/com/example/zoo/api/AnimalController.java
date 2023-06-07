package com.example.zoo.api;

import com.example.zoo.firebase.FirebaseStorageStrategy;
import com.example.zoo.model.Animal;
import com.example.zoo.model.Image;
import com.example.zoo.service.AnimalService;
import com.example.zoo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final ImageService imageService;

    private final FirebaseStorageStrategy firebaseStorageStrategy;

    @Autowired
    public AnimalController(AnimalService animalService, ImageService imageService, FirebaseStorageStrategy firebaseStorageStrategy) {
        this.animalService = animalService;
        this.imageService = imageService;
        this.firebaseStorageStrategy = firebaseStorageStrategy;
    }

    @GetMapping
    public List<Animal> getAnimals() {
        return animalService.getAllAnimals();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getAnimalById(@PathVariable("id") UUID id) {
        if(animalService.getAnimalById(id).isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        return ResponseEntity.ok().body(animalService.getAnimalById(id).get().toString().getBytes());
    }

    @GetMapping("{name}")
    public ResponseEntity<byte[]> getAnimalByName(@PathVariable("name") String name) {
        if(animalService.getAnimalByName(name).isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        return ResponseEntity.ok().body(animalService.getAnimalByName(name).get().toString().getBytes());
    }

    @GetMapping("type/{type}")
    public List<Animal> getAnimalsByType(@PathVariable("type") String type) {
        return animalService.getAnimalsByType(type);
    }

    @GetMapping("climate/{climate}")
    public List<Animal> getAnimalsByClimate(@PathVariable("climate") String climate) {
        return animalService.getAnimalsByClimate(climate);
    }

    @GetMapping("conservation/{conservation}")
    public List<Animal> getAnimalsByConservation(@PathVariable("conservation") String conservation) {
        return animalService.getAnimalsByConservation(conservation);
    }

    @GetMapping("region/{origin}")
    public List<Animal> getAnimalsByOrigin(@PathVariable("origin") String origin) {
        return animalService.getAnimalsByOrigin(origin);
    }

    @GetMapping("search={search}")
    public List<Animal> getAnimalsBySearch(@PathVariable("search") String search) {
        return animalService.getAnimalsBySearch(search);
    }

    @GetMapping("{name}/photo")
    public ResponseEntity<byte[]> getAnimalPhotoById(@PathVariable("name") String name) {
        Optional<Animal> animal = animalService.getAnimalByName(name);
        if(animal.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        Optional<Image> image = imageService.getImageByAnimalId(animal.get().getId());
        if(image.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Image not found".getBytes());
        try{
            byte[] img = firebaseStorageStrategy.download(image.get().getLocation());
            return ResponseEntity
                    .status(OK)
                    .contentType(MediaType.valueOf(image.get().getType()))
                    .body(img);
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body("Image not found".getBytes());
        }
    }
    /*
    @PostMapping("criteria")
     */

    @PostMapping
    public ResponseEntity<byte[]> addAnimal(@RequestBody Animal animal) {
        if(animalService.addAnimal(animal) == 0)
            return ResponseEntity.badRequest().body("Animal already exists or is invalid".getBytes());
        return ResponseEntity.status(CREATED).body("Animal added successfully".getBytes());
    }

    @DeleteMapping("{name}")
    public ResponseEntity<byte[]> deleteAnimalByName(@PathVariable("name") String name) {
        if(animalService.deleteAnimal(name) == 0)
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{name}")
    public ResponseEntity<byte[]> updateAnimalByName(@PathVariable("name") String name, @RequestBody Animal animal) {
        if(animalService.updateAnimal(name, animal) == 0)
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{name}/photo")
    public ResponseEntity<byte[]> uploadAnimalImage(@PathVariable("name") String name, @RequestParam("image")MultipartFile image) {
        Optional<Animal> animal = animalService.getAnimalByName(name);
        if(animal.isEmpty())
            return ResponseEntity.status(NOT_FOUND).body("Animal not found".getBytes());
        if(imageService.uploadImage(animal.get().getId(), image) == 0)
            return ResponseEntity.status(NOT_ACCEPTABLE).body("Image is invalid".getBytes());
        return ResponseEntity.status(NO_CONTENT).body("Image uploaded successfully".getBytes());
    }
}
