package com.example.amazon.Product;

import com.example.amazon.Category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;
    private String description;
    private String manufacturer;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_value", referencedColumnName = "value")
    private Category category;

    @Enumerated(EnumType.STRING)
    private Region region;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Product(ProductRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.manufacturer = request.getManufacturer();
        this.price = request.getPrice();
        this.category = new Category(request.getCategory());
        this.region = Region.valueOf(request.getRegion());
    }
}
