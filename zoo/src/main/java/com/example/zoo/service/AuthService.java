package com.example.zoo.service;

import com.example.zoo.model.AuthenticationResponse;
import com.example.zoo.model.LoginRequest;
import com.example.zoo.model.RegisterRequest;
import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public boolean validateNewUser(User user) {
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()
                || user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getPhone().isEmpty())
            return false;
        if(repository.findUserByUsername(user.getUsername()).isPresent())
            return false;
        return repository.findUserByEmail(user.getEmail()).isEmpty();
    }
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = repository.findUserByUsername(request.getUsername()).orElseThrow();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        String jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), request.getUsername(),
                request.getEmail(), request.getPhone(), passwordEncoder.encode(request.getPassword()));
        repository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        String jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
