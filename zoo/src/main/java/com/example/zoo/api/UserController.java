package com.example.zoo.api;

import com.example.zoo.model.User;
import com.example.zoo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<byte[]> deleteUserById(@PathVariable UUID id) {
        if (userService.deleteUser(id) == 0)
            return ResponseEntity.badRequest().body("User not found".getBytes());
        return ResponseEntity.ok().body("User deleted".getBytes());
    }
}
    /*@GetMapping(path = "/self")
    public ResponseEntity<byte[]> getSelf(){

    }*/
