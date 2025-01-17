package com.scheduleProjector.scheduleProjector.controller;

import com.scheduleProjector.scheduleProjector.response.LoginResponse;
import com.scheduleProjector.scheduleProjector.dto.UserDto;
import com.scheduleProjector.scheduleProjector.model.User;
import com.scheduleProjector.scheduleProjector.service.AuthenticationService;
import com.scheduleProjector.scheduleProjector.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserDto UserDto) {
        User registeredUser = authenticationService.signup(UserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserDto UserDto){
        User authenticatedUser = authenticationService.authenticate(UserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
