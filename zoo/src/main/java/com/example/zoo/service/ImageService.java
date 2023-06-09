package com.example.zoo.service;

import com.example.zoo.firebase.FirebaseStorageStrategy;
import com.example.zoo.model.Animal;
import com.example.zoo.model.Image;
import com.example.zoo.repository.ImageRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
public class ImageService {

    private final ImageRepo imageRepo;

    private final AnimalService animalService;

    private final FirebaseStorageStrategy firebaseStorageStrategy;

    public ImageService(ImageRepo imageRepo, AnimalService animalService, FirebaseStorageStrategy firebaseStorageStrategy) {
        this.imageRepo = imageRepo;
        this.animalService = animalService;
        this.firebaseStorageStrategy = firebaseStorageStrategy;
    }
    private static final Logger logger = Logger.getLogger(AnimalService.class.getName());

    public Optional<Image> getImageByAnimalId(UUID animalId) {
        return imageRepo.findImageByAnimalId(animalId);
    }

    public int addImage(Image image, UUID animalId) {
        if(animalService.getAnimalById(animalId).isPresent())
        {
            image.setAnimal(animalService.getAnimalById(animalId).get());
            imageRepo.save(image);
            return 1;
        }
        return 0;
    }

    public int deleteImage(UUID imageId) {
        imageRepo.deleteById(imageId);
        return 1;
    }

    public int uploadImage(UUID animalId, MultipartFile image) {
        Optional<Animal> animal = animalService.getAnimalById(animalId);
        if(animal.isEmpty())
            return 0;
        Optional<Image> oldImage = getImageByAnimalId(animalId);
        String oldImageLocation = "";
        if(oldImage.isPresent()) {
            oldImageLocation = oldImage.get().getLocation();
            deleteImage(oldImage.get().getId());
        }

        String animalName = animal.get().getName();

        String fileName = animalName + "/" + image.getOriginalFilename();
        String fileType = image.getContentType();
        Image image1 = new Image(image.getOriginalFilename(), fileName, fileType);
        if(addImage(image1, animalId) == 0)
            return 0;
        try {
            firebaseStorageStrategy.deleteFile(oldImageLocation);
            firebaseStorageStrategy.upload(image, image.getOriginalFilename(), animalName);
            return 1;
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Exception: ", e);
            return 0;
        }

    }
}
