package com.networkguard.ai_recommendation_service.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
public class AITestController {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @GetMapping("/ai/test")
    public String testGroq() {

        RestClient client = RestClient.create();

        Map<String, Object> request = Map.of(
                "model", "openai/gpt-oss-20b",
                "messages", new Object[] {
                        Map.of(
                                "role", "user",
                                "content", "Say hello from NetworkGuardAI"
                        )
                }
        );

        return client.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(request)
                .retrieve()
                .body(String.class);
    }
}