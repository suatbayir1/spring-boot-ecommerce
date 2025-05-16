package com.example.amazon.Product.CommandHandlers;

import com.example.amazon.Product.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateProductCommand {
    private UUID uuid;
    private ProductRequest request;
}
