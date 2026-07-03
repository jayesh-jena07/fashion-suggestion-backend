package com.example.FashionRecommendationApp.controller;

import com.example.FashionRecommendationApp.dto.SkinAnalysisResponse;
import com.example.FashionRecommendationApp.service.SkinAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skin")
public class SkinAnalysisController {

    private final SkinAnalysisService skinAnalysisService;

    // Spring Boot automatically injects your service here
    public SkinAnalysisController(SkinAnalysisService skinAnalysisService) {
        this.skinAnalysisService = skinAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<SkinAnalysisResponse> analyzeSkin(@RequestParam String imagePath) {
        try {
            SkinAnalysisResponse response = skinAnalysisService.analyzeSkin(imagePath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // If something goes wrong (like Python missing a library), it returns a 500 status
            return ResponseEntity.internalServerError().build();
        }
    }
}