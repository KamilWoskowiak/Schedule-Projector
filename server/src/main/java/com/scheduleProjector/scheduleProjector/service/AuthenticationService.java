package com.scheduleProjector.scheduleProjector.service;

import com.scheduleProjector.scheduleProjector.dto.UserDto;
import com.scheduleProjector.scheduleProjector.model.User;
import com.scheduleProjector.scheduleProjector.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(UserDto input) {
        User user = new User(input.getEmail(), passwordEncoder.encode(input.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(UserDto input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return user;
    }
}
