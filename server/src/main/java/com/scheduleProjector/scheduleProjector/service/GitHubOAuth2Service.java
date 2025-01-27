package com.scheduleProjector.scheduleProjector.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GitHubOAuth2Service {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getEmailFromIdToken(String idTokenString) {
        String url = "https://api.github.com/user";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(idTokenString);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        Map<String, Object> body = response.getBody();
        if (body == null) return null;

        String email = (String) body.get("email");
        if (email != null) {
            return email;
        }

        String emailsUrl = "https://api.github.com/user/emails";

        ResponseEntity<List> emailsResponse = restTemplate.exchange(emailsUrl, HttpMethod.GET, request, List.class);
        if (!emailsResponse.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        List<Map<String, Object>> emailsList = emailsResponse.getBody();
        if (emailsList == null) {
            return null;
        }

        for (Map<String, Object> emailObj : emailsList) {
            if ((Boolean) emailObj.get("primary")) {
                return (String) emailObj.get("email");
            }
        }

        return null;

    }

}
