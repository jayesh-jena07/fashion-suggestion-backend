package com.example.FashionRecommendationApp.service;

import com.example.FashionRecommendationApp.dto.RecommendationResponse;
import com.example.FashionRecommendationApp.model.ProductInventory;
import com.example.FashionRecommendationApp.repository.ProductInventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FashionRecommendationService {

    private final ProductInventoryRepository inventoryRepository;

    // Dependency injection via constructor
    public FashionRecommendationService(ProductInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Accepts a skin undertone and its matching complementary colors,
     * queries the database for Tops and Pants, and returns a structured response.
     */
    public RecommendationResponse getRecommendations(String undertone, List<String> matchingColors) {

        // Convert all colors coming from the AI script to lowercase safely
        List<String> lowerCaseColors = matchingColors.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        // 1. Fetch matching Tops from the database
        List<ProductInventory> tops = inventoryRepository.findByCategoryAndColorIn("Top", lowerCaseColors);

        // 2. Fetch matching Pants from the database
        List<ProductInventory> pants = inventoryRepository.findByCategoryAndColorIn("Pants", lowerCaseColors);

        return new RecommendationResponse(undertone, tops, pants);
    }
}