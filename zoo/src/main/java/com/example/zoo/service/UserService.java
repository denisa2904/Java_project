package com.example.zoo.service;

import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public boolean validateNewUser(User user) {
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()
                || user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getPhone().isEmpty())
            return false;
        if(userRepo.findUserByUsername(user.getUsername()).isPresent())
            return false;
        return userRepo.findUserByEmail(user.getEmail()).isEmpty();
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
}