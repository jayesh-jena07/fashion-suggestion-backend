package com.example.FashionRecommendationApp.controller;

import com.example.FashionRecommendationApp.model.ProductInventory;
import com.example.FashionRecommendationApp.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryRepository inventoryRepository;

    @PostMapping("/bulk-add")
    public ResponseEntity<String> bulkAddProducts(@RequestBody List<ProductInventory> products) {
        try {
            // Save the collection incoming from the Python OpenCV pipeline straight to the DB
            inventoryRepository.saveAll(products);
            return ResponseEntity.ok("🎉 Successfully saved " + products.size() + " products to inventory!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error storing inventory records: " + e.getMessage());
        }
    }
}