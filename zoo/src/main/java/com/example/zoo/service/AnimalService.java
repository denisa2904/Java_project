package com.example.zoo.service;

import com.example.zoo.model.Animal;
import com.example.zoo.model.Criteria;
import com.example.zoo.repository.AnimalRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@Transactional
public class AnimalService {

    private final AnimalRepo animalRepo;

    @Autowired
    public AnimalService(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    public boolean validateNewAnimal(Animal animal) {
        if(animal.getName().isEmpty())
            return false;
        List<Animal> animals = animalRepo.findAll();
        for(Animal a : animals) {
            if(a.getName().equals(animal.getName()))
                return false;
        }
        return true;
    }

    public int addAnimal(Animal animal) {
        if(validateNewAnimal(animal)) {
            animalRepo.save(animal);
            return 1;
        }
        return 0;
    }

    public List<Animal> getAllAnimals() {
        return animalRepo.findAll();
    }

    public Optional<Animal> getAnimalById(UUID id) {
        return animalRepo.findById(id);
    }

    public Optional<Animal> getAnimalByName(String name) {
        return animalRepo.findAnimalByName(name);
    }

    public List<Animal> getAnimalsByType(String type) {
        return animalRepo.findAllByType(type);
    }

    public List<Animal> getAnimalsByClimate(String climate) {
        return animalRepo.findAllByClimate(climate);
    }

    public List<Animal> getAnimalsByConservation(String conservation) {
        return animalRepo.findAllByConservation(conservation);
    }

    public List<Animal> getAnimalsByOrigin(String origin) {
        return animalRepo.findAllByOrigin(origin);
    }

    public List<Animal> getAnimalsBySearch(String search) {
        List<Animal> animals = new ArrayList<>();
        animals.addAll(animalRepo.findAllByNameContaining(search));
        animals.addAll(animalRepo.findAllByTypeContaining(search));
        animals.addAll(animalRepo.findAllByClimateContaining(search));
        animals.addAll(animalRepo.findAllByConservationContaining(search));
        animals.addAll(animalRepo.findAllByOriginContaining(search));
        animals.addAll(animalRepo.findAllByBinomialNameContaining(search));
        animals.addAll(animalRepo.findAllByDescriptionContaining(search));
        return animals;
    }

    public List<Animal> getAnimalsByCriteria(Criteria criteria){
        List<Animal> animals = new ArrayList<>();
        Map<String, List<String>> criteriaMap = new HashMap<>();
        System.out.println(criteria);
        criteriaMap = criteria.getCriteria();
        System.out.println(criteriaMap);
        for(Map.Entry<String, List<String>> entry : criteriaMap.entrySet()){
            if(entry.getKey().equals("type")){
                for(String type : entry.getValue()){
                    animals.addAll(animalRepo.findAllByTypeContaining(type));
                }
            }
            else if(entry.getKey().equals("climate")){
                for(String climate : entry.getValue()){
                    animals.addAll(animalRepo.findAllByClimateContaining(climate));
                }
            }
            else if(entry.getKey().equals("conservation")){
                for(String conservation : entry.getValue()){
                    animals.addAll(animalRepo.findAllByConservation(conservation));
                }
            }
            else if(entry.getKey().equals("origin")){
                for(String origin : entry.getValue()){
                    animals.addAll(animalRepo.findAllByOriginContaining(origin));
                }
            }
        }
        return animals;
    }
    public int deleteAnimal(String name) {
        Optional<Animal> animal = animalRepo.findAnimalByName(name);
        if(animal.isPresent()) {
            animalRepo.delete(animal.get());
            return 1;
        }
        return 0;
    }

    public int updateAnimal(String name, Animal animal) {
        Optional<Animal> oldAnimal = animalRepo.findAnimalByName(name);
        if(oldAnimal.isPresent()) {
            Animal newAnimal = oldAnimal.get();
            if(animal.getType() != null)
                newAnimal.setType(animal.getType());
            if(animal.getClimate()!=null)
                newAnimal.setClimate(animal.getClimate());
            if(animal.getConservation()!=null)
                newAnimal.setConservation(animal.getConservation());
            if(animal.getOrigin()!=null)
                newAnimal.setOrigin(animal.getOrigin());
            animalRepo.save(newAnimal);
            return 1;
        }
        return 0;
    }



}
