package com.scheduleProjector.scheduleProjector.controller;

import com.scheduleProjector.scheduleProjector.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/success")
    public ResponseEntity<?> success(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.badRequest().body("No authentication found");
        }

        String email = authentication.getName();

        String accessToken = tokenService.generateToken(email);
        String refreshToken = tokenService.generateRefreshToken(email);

        return ResponseEntity.ok().body(
                "Access Token: " + accessToken + "\nRefresh Token: " + refreshToken
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        if (!tokenService.validateToken(refreshToken)) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        Claims claims = tokenService.getClaims(refreshToken);
        String email = claims.getSubject();

        String newAccessToken = tokenService.generateToken(email);
        return ResponseEntity.ok().body("New Access Token: " + newAccessToken);
    }

    public static class RefreshRequest {
        private String refreshToken;
        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    }
}