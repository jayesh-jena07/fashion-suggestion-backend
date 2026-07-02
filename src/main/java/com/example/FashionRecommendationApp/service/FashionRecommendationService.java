package com.example.FashionRecommendationApp.service;

import com.example.FashionRecommendationApp.dto.RecommendationResponse;
import com.example.FashionRecommendationApp.model.ProductInventory;
import com.example.FashionRecommendationApp.repository.ProductInventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // 1. Fetch matching Tops from the H2 database
        List<ProductInventory> tops = inventoryRepository.findByCategoryAndColorIn("Top", matchingColors);

        // 2. Fetch matching Pants from the H2 database
        List<ProductInventory> pants = inventoryRepository.findByCategoryAndColorIn("Pants", matchingColors);

        // 3. Wrap everything cleanly inside our Data Transfer Object (DTO)
        return new RecommendationResponse(undertone, tops, pants);
    }
}