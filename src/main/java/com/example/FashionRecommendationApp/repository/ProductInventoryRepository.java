package com.example.FashionRecommendationApp.repository;

import com.example.FashionRecommendationApp.model.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

    // Automatically generates: SELECT * FROM product_inventory WHERE category = ? AND color IN (...)
    List<ProductInventory> findByCategoryAndColorIn(String category, List<String> colors);
}