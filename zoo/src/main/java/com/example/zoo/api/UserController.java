package com.example.zoo.api;

import com.example.zoo.model.Animal;
import com.example.zoo.model.AnimalHelper;
import com.example.zoo.model.User;
import com.example.zoo.service.AnimalService;
import com.example.zoo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;
    private final AnimalService animalService;

    public UserController(UserService userService, AnimalService animalService) {
        this.userService = userService;
        this.animalService = animalService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID id) {
        if (userService.deleteUser(id) == 0)
            return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.status(204).body("User deleted");
    }

    @DeleteMapping(path = "/deleteSelf")
    public ResponseEntity<?> deleteSelf(@NonNull HttpServletRequest request) {
        String username = userService.getUsernameFromJwt(request);
        UUID id = userService.getUserByUsername(username).getId();
        if (userService.deleteUser(id) == 0)
            return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.status(204).body("User deleted");
    }

    @GetMapping(path = "/self")
    public ResponseEntity<?> getSelf(@NonNull HttpServletRequest request) {
        String username = userService.getUsernameFromJwt(request);
        User user = userService.getUserByUsername(username);
        if (user == null)
            return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.status(200).body(user);
    }

    @PatchMapping(path = "/updateSelf")
    public ResponseEntity<?> updateSelf(@NonNull HttpServletRequest request, @RequestBody User user) {
        String username = userService.getUsernameFromJwt(request);
        User userToUpdate = userService.getUserByUsername(username);
        if (userToUpdate == null)
            return ResponseEntity.status(404).body("User not found");
        if(user.getEmail()!=null)
            userToUpdate.setEmail(user.getEmail());
        if(user.getFirstName()!=null)
            userToUpdate.setFirstName(user.getFirstName());
        if(user.getLastName()!=null)
            userToUpdate.setLastName(user.getLastName());
        if(user.getPhone()!=null)
            userToUpdate.setPhone(user.getPhone());
        if(user.getTheme()!=null)
            userToUpdate.setTheme(user.getTheme());

        if(userService.updateUser(userToUpdate.getId(), userToUpdate)==1)
            return ResponseEntity.status(204).body("User updated");
        return ResponseEntity.status(404).body("User not found");
    }

    @GetMapping(path = "/favorites")
    public ResponseEntity<?> getFavorites(@NonNull HttpServletRequest request) {
        String username = userService.getUsernameFromJwt(request);
        User user = userService.getUserByUsername(username);
        if (user == null)
            return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.status(200).body(userService.getUserFavorites(user.getId()));
    }

    @PostMapping(path = "/favorites")
    public ResponseEntity<?> addFavorite(@NonNull HttpServletRequest request, @RequestBody AnimalHelper animalHelper) {
        String username = userService.getUsernameFromJwt(request);
        User user = userService.getUserByUsername(username);
        if (user == null)
            return ResponseEntity.status(404).body("User not found");
        Optional<Animal> animalMaybe = animalService.getAnimalById(animalHelper.getId());
        if (animalMaybe.isEmpty())
            return ResponseEntity.status(404).body("Animal not found");
        Animal animal = animalMaybe.get();
        if (userService.addFavorite(user.getId(), animal) == 1)
            return ResponseEntity.status(201).body("CREATED");
        return ResponseEntity.status(400).body("BAD REQUEST");
    }

    @DeleteMapping(path = "/favorites/{animalId}")
    public ResponseEntity<?> deleteFavorite(@NonNull HttpServletRequest request, @PathVariable("animalId") UUID animalId) {
        String username = userService.getUsernameFromJwt(request);
        User user = userService.getUserByUsername(username);
        Optional<Animal> animalMaybe = animalService.getAnimalById(animalId);
        if (animalMaybe.isEmpty())
            return ResponseEntity.status(404).body("Animal not found");
        Animal animal = animalMaybe.get();
        if (userService.removeFavorite(user.getId(), animal) == 1)
            return ResponseEntity.status(204).body("DELETED");
        return ResponseEntity.status(404).body("User not found");
    }

}

