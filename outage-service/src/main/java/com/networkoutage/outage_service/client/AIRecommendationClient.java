package com.networkoutage.outage_service.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "AI-RECOMMENDATION-SERVICE")
public interface AIRecommendationClient {

    @PostMapping("/ai/recommend")
    String getRecommendation(@RequestBody Map<String, String> outage);
}