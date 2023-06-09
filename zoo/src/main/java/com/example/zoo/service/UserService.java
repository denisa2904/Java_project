package com.example.zoo.service;

import com.example.zoo.model.Animal;
import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepo userRepo, JwtService jwtService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    public boolean validateNewUser(User user) {
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()
                || user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getPhone().isEmpty())
            return false;
        if(userRepo.findUserByUsername(user.getUsername()).isPresent())
            return false;
        return userRepo.findUserByEmail(user.getEmail()).isEmpty();
    }

    public String getUsernameFromJwt(@NonNull HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String jwt;
        jwt = authHeader.substring(7);
        return jwtService.extractUsername(jwt);
    }

    public int addUser(User user) {
        if(validateNewUser(user)) {
            userRepo.save(user);
            return 1;
        }
        return 0;
    }

    public Optional<User> getUserById(UUID id) {
        return userRepo.findUserById(id);
    }

    public User getUserByUsername(String username) {
        return userRepo.findUserByUsername(username).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public int deleteUser(UUID userId) {
        Optional<User> user = userRepo.findUserById(userId);
        if(user.isPresent()) {
            userRepo.delete(user.get());
            return 1;
        }
        return 0;
    }

    public int updateUser(UUID userId,User user) {
        Optional<User> oldUser = userRepo.findUserById(userId);
        if(oldUser.isPresent()) {
            User newUser = oldUser.get();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setPhone(user.getPhone());
            userRepo.save(newUser);
            return 1;
        }
        return 0;
    }

    public List<Animal> getUserFavorites(UUID userId) {
        return userRepo.findUserById(userId).get().getFavorites();
    }

    public int addFavorite(UUID userId, Animal animal) {
        Optional<User> user = userRepo.findUserById(userId);
        if(user.isPresent()) {
            User newUser = user.get();
            newUser.addFavorite(animal);
            userRepo.save(newUser);
            return 1;
        }
        return 0;
    }

    public int removeFavorite(UUID userId, Animal animal) {
        Optional<User> user = userRepo.findUserById(userId);
        if(user.isPresent()) {
            User newUser = user.get();
            newUser.removeFavorite(animal);
            userRepo.save(newUser);
            return 1;
        }
        return 0;
    }
}
