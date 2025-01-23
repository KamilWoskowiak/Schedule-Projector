package com.scheduleProjector.scheduleProjector.service;

import com.scheduleProjector.scheduleProjector.repository.UserRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "github"

        String email = (String) oAuth2User.getAttributes().get("email");

        if ("github".equalsIgnoreCase(registrationId) && email == null) {
            String tokenValue = userRequest.getAccessToken().getTokenValue();

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(tokenValue);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    "https://api.github.com/user/emails",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );
            List<Map<String, Object>> emails = response.getBody();
            if (emails != null) {
                for (Map<String, Object> e : emails) {
                    System.out.println(e);
                    System.out.println(e.get("primary"));
                    Boolean primary = (Boolean) e.get("primary");
                    if (primary) {
                        email = (String) e.get("email");
                        break;
                    }
                }
            }
        }

        if (email == null) {
            // Depending on your requirements:
            throw new RuntimeException("Could not retrieve email from GitHub (user has no public primary email).");
        }

        return oAuth2User;
    }
}