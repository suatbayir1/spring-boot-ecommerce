package com.example.amazon.Product;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class ProductDTO {
    private UUID uuid;
    private String name;
    private String description;
    private String manufacturer;
    private Double price;
    private String category;

    public ProductDTO(Product product) {
        this.uuid = product.getUuid();
        this.name = product.getName();
        this.description = product.getDescription();
        this.manufacturer = product.getManufacturer();
        this.price = product.getPrice();
        this.category = product.getCategory().getValue();
    }
}
