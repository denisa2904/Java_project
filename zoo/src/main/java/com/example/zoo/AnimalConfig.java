package com.example.zoo;

import com.example.zoo.model.Animal;
import com.example.zoo.repository.AnimalRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalConfig {

    @Bean
    CommandLineRunner commandLineRunner(AnimalRepo repo) {
        return args -> {
            for(int i = 1; i <= 10; ++i)
            {
                /*Player player = new Player(faker.name().username());
                repo.save(player);*/
                Animal animal = new Animal("Animal" + i, "Binomial" + i, "Mammal", "Temperate", "Least Concern", "Europe", "Description", 3.5f, i*100, i*200);
                repo.save(animal);
            }
        };
    }
}
