package com.example.zoo.api;

import com.example.zoo.model.AuthenticationResponse;
import com.example.zoo.model.LoginRequest;
import com.example.zoo.model.RegisterRequest;
import com.example.zoo.model.User;
import com.example.zoo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        if(!service.validateNewUser(new User(
                request.getFirstName(), request.getLastName(), request.getUsername(),
                request.getEmail(), request.getPhone(), request.getPassword()
        ))){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON)
                    .body(AuthenticationResponse.builder().token("Invalid").build());
        }
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}
