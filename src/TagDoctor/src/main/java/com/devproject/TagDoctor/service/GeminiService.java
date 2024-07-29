package com.devproject.TagDoctor.service;

import com.devproject.TagDoctor.dto.GeminiRequest;
import com.devproject.TagDoctor.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GeminiService {
    private final RestTemplate restTemplate;
    private final String apiKey;

    public GeminiService(RestTemplate restTemplate, @Value("${googleAi.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public GeminiResponse getCompletion(String model, GeminiRequest request) {
        String url = "/v1beta/models/" + model + ":generateContent";

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-goog-api-key", apiKey);
        headers.set("Content-type", "application/json");
        headers.set("Accept", "application/json");

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                GeminiResponse.class
        );

        return response.getBody();
    }
}
