package com.networkguard.ai_recommendation_service.controller;





import org.springframework.web.bind.annotation.*;

import com.networkguard.ai_recommendation_service.dto.OutageRequest;
import com.networkguard.ai_recommendation_service.service.AIRecommendationService;

@RestController
@RequestMapping("/ai")
public class AIRecommendationController {

    private final AIRecommendationService aiRecommendationService;

    public AIRecommendationController(
            AIRecommendationService aiRecommendationService) {
        this.aiRecommendationService = aiRecommendationService;
    }

    @PostMapping("/recommend")
    public String getRecommendation(@RequestBody OutageRequest outage) {

        return aiRecommendationService.generateRecommendation(outage);
    }
}