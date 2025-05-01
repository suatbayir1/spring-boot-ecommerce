package com.example.amazon.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final DeleteProductCommandHandler deleteProductCommandHandler;

    public ProductController(
            ProductRepository productRepository,
            DeleteProductCommandHandler deleteProductCommandHandler
    ) {
        this.productRepository = productRepository;
        this.deleteProductCommandHandler = deleteProductCommandHandler;
    }

    @GetMapping("/check")
    public List<Product> check() {
        return productRepository.findAll();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteProductById(@PathVariable UUID uuid) {
        return deleteProductCommandHandler.execute(uuid);
    }
}
