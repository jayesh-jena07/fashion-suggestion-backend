package com.example.FashionRecommendationApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_inventory")
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String platform;   // "Myntra" or "Ajio"
    private String category;   // "Tops" or "Pants"
    private String color;      // Standardized color token (e.g., "White", "Navy Blue")

    private String title;
    private Double price;

    @Column(columnDefinition = "TEXT")
    private String productUrl;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    // Default constructor required by JPA
    public ProductInventory() {}

    // Parametrized constructor for seeding data easily
    public ProductInventory(String platform, String category, String color, String title, Double price, String productUrl, String imageUrl) {
        this.platform = platform;
        this.category = category;
        this.color = color;
        this.title = title;
        this.price = price;
        this.productUrl = productUrl;
        this.imageUrl = imageUrl;
    }
}