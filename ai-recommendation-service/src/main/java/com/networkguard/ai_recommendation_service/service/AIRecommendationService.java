package com.networkguard.ai_recommendation_service.service;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.networkguard.ai_recommendation_service.dto.OutageRequest;

import java.util.Map;

@Service
public class AIRecommendationService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    public String generateRecommendation(OutageRequest outage) {

        RestClient client = RestClient.create();

        String prompt = """
                You are an AI network outage troubleshooting assistant.

                Analyze the following network outage:

                Title: %s
                Description: %s
                Location: %s
                Severity: %s

                Provide:
                1. Probable cause
                2. Troubleshooting steps
                3. Suggested priority
                4. Suitable technician specialization

                Give a clear and concise response.
                """.formatted(
                outage.getTitle(),
                outage.getDescription(),
                outage.getLocation(),
                outage.getSeverity()
        );

        Map<String, Object> request = Map.of(
                "model", "openai/gpt-oss-20b",
                "messages", new Object[] {
                        Map.of(
                                "role", "user",
                                "content", prompt
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