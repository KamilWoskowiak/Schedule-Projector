package com.scheduleProjector.scheduleProjector.controller;

import com.scheduleProjector.scheduleProjector.dto.AuthDto;
import com.scheduleProjector.scheduleProjector.dto.LoginDto;
import com.scheduleProjector.scheduleProjector.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto loginDto
    ) {
        String provider = loginDto.getProvider();
        String idTokenString = loginDto.getTokenId();

        if (provider == null || idTokenString == null) {
            return ResponseEntity.badRequest().body("Missing provider or tokenId");
        }

        try {
            AuthDto authDto = authService.auth(provider, idTokenString);

            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authDto.getRefreshToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/api/auth")
                    .maxAge(7L * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("accessToken", authDto.getAccessToken());
            responseBody.put("message", "Successfully logged in");

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body(responseBody);
        } catch (GeneralSecurityException e) {
            return ResponseEntity.status(401).body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(name="refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("No refresh token provided");
        }

        try {
            String newAccessToken = authService.refreshAccessToken(refreshToken);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("accessToken", newAccessToken);
            responseBody.put("message", "Access token refreshed successfully");

            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
