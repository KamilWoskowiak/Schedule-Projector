package com.scheduleProjector.scheduleProjector.controller;

import com.scheduleProjector.scheduleProjector.model.User;
import com.scheduleProjector.scheduleProjector.repository.UserRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class auth {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String email, @RequestParam String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("User with email " + email + " already exists");
        }

        User newUser = new User(email, passwordEncoder.encode(password));
        userRepository.save(newUser);
        return ResponseEntity
                .ok("User registered successfully");
    }
}
