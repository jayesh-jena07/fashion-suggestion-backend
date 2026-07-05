package com.example.FashionRecommendationApp.controller;

import com.example.FashionRecommendationApp.dto.RecommendationResponse;
import com.example.FashionRecommendationApp.dto.SkinAnalysisResponse;
import com.example.FashionRecommendationApp.service.FashionRecommendationService;
import com.example.FashionRecommendationApp.service.SkinAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skin")
public class SkinAnalysisController {

    private final SkinAnalysisService skinAnalysisService;
    private final FashionRecommendationService fashionRecommendationService;

    // Inject both services via the constructor
    public SkinAnalysisController(SkinAnalysisService skinAnalysisService,
                                  FashionRecommendationService fashionRecommendationService) {
        this.skinAnalysisService = skinAnalysisService;
        this.fashionRecommendationService = fashionRecommendationService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<RecommendationResponse> analyzeSkinAndRecommend(@RequestParam String imagePath) {
        try {
            // Step 1: Run the AI skin analysis script via the OS process bridge
            SkinAnalysisResponse skinProfile = skinAnalysisService.analyzeSkin(imagePath);

            // Step 2: Pass the undertone and color spectrum straight into the inventory engine
            RecommendationResponse recommendations = fashionRecommendationService.getRecommendations(
                    skinProfile.getUndertone(),
                    skinProfile.getMatchingColors()
            );

            // Step 3: Return the beautiful, full outfit breakdown to the client
            return ResponseEntity.ok(recommendations);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}