package com.example.FashionRecommendationApp.controller;

import com.example.FashionRecommendationApp.dto.RecommendationResponse;
import com.example.FashionRecommendationApp.service.FashionRecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final FashionRecommendationService recommendationService;

    public RecommendationController(FashionRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Endpoint to manually test recommendations by passing values directly.
     * URL: POST http://localhost:8080/api/recommendations/test
     */
    @PostMapping("/test")
    public RecommendationResponse testRecommendations(
            @RequestParam String undertone,
            @RequestParam List<String> matchingColors) {

        return recommendationService.getRecommendations(undertone, matchingColors);
    }
}