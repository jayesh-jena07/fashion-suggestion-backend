package com.example.FashionRecommendationApp.config;

import com.example.FashionRecommendationApp.model.ProductInventory;
import com.example.FashionRecommendationApp.repository.ProductInventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductInventoryRepository repository;

    // Constructor Injection
    public DataInitializer(ProductInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only seed if the database table is completely empty
        if (repository.count() == 0) {
            System.out.println(">>> Seeding initial mock fashion inventory to H2 DB...");

            repository.saveAll(Arrays.asList(
                    // --- TOPS ---
                    new ProductInventory("Myntra", "Tops", "Olive Green", "Roadster Casual Shirt", 899.00, "https://myntra.com/items/1", "https://images.myntra.com/1.jpg"),
                    new ProductInventory("Ajio", "Tops", "Navy Blue", "Dennis Lingo Slim Fit Shirt", 699.00, "https://ajio.com/items/2", "https://images.ajio.com/2.jpg"),
                    new ProductInventory("Myntra", "Tops", "White", "HRX Cotton T-Shirt", 499.00, "https://myntra.com/items/3", "https://images.myntra.com/3.jpg"),
                    new ProductInventory("Ajio", "Tops", "Burgundy", "Netplay Polo Shirt", 599.00, "https://ajio.com/items/4", "https://images.ajio.com/4.jpg"),

                    // --- PANTS ---
                    new ProductInventory("Myntra", "Pants", "Beige", "Highlander Slim Chinos", 1199.00, "https://myntra.com/items/5", "https://images.myntra.com/5.jpg"),
                    new ProductInventory("Ajio", "Pants", "Black", "Levis Stretchable Jeans", 2499.00, "https://ajio.com/items/6", "https://images.ajio.com/6.jpg"),
                    new ProductInventory("Myntra", "Pants", "Grey", "Wrong Regular Fit Trousers", 1499.00, "https://myntra.com/items/7", "https://images.myntra.com/7.jpg")
            ));

            System.out.println(">>> Seed successful! Total clothes available in H2: " + repository.count());
        }
    }
}