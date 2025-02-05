package com.scheduleProjector.scheduleProjector.service;

import com.scheduleProjector.scheduleProjector.dto.AuthDto;
import com.scheduleProjector.scheduleProjector.model.User;
import com.scheduleProjector.scheduleProjector.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final GitHubOAuth2Service gitHubOAuth2Service;
    private final GoogleOAuth2Service googleOAuth2Service;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthService(GitHubOAuth2Service gitHubService,
                       GoogleOAuth2Service googleService,
                       UserRepository userRepository,
                       JwtUtils jwtUtils) {
        this.gitHubOAuth2Service = gitHubService;
        this.googleOAuth2Service = googleService;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public AuthDto auth(String provider, String idTokenString) throws Exception {
        String email;

        switch (provider) {
            case "github" -> email = gitHubOAuth2Service.getEmailFromIdToken(idTokenString);
            case "google" -> email = googleOAuth2Service.getEmailFromIdToken(idTokenString);
            default -> throw new IllegalArgumentException("Invalid provider: " + provider);
        }

        if (email == null) throw new IllegalArgumentException("Invalid provider: " + provider + " or token: " + idTokenString);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            User existingUser = user.get();
            if (!existingUser.getProvider().equalsIgnoreCase(provider)) {
                throw new IllegalArgumentException("User already exists with different provider: " + existingUser.getProvider());
            }
            return new AuthDto(jwtUtils.generateAccessToken(email), jwtUtils.generateRefreshToken(email));
        } else {
            User newUser = new User(email, provider);
            userRepository.save(newUser);
            return new AuthDto(jwtUtils.generateAccessToken(email), jwtUtils.generateRefreshToken(email));
        }
    }

    public String refreshAccessToken(String refreshToken) {
        String email = jwtUtils.validateRefreshToken(refreshToken);
        return jwtUtils.generateAccessToken(email);
    }

    public String nullRefreshToken() {
        return jwtUtils.generateRefreshToken(null);
    }
}
