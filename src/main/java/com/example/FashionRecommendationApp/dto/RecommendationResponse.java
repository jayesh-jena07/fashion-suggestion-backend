package com.example.FashionRecommendationApp.dto;

import com.example.FashionRecommendationApp.model.ProductInventory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class RecommendationResponse {

    private String detectedUndertone;
    private List<ProductInventory> recommendedTops;
    private List<ProductInventory> recommendedPants;

    // No-args constructor
    public RecommendationResponse() {}

    // All-args constructor
    public RecommendationResponse(String detectedUndertone, List<ProductInventory> recommendedTops, List<ProductInventory> recommendedPants) {
        this.detectedUndertone = detectedUndertone;
        this.recommendedTops = recommendedTops;
        this.recommendedPants = recommendedPants;
    }

  }