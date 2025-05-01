package com.example.amazon.Product;

import com.example.amazon.Command;
import com.example.amazon.Exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteProductCommandHandler implements Command<UUID, Void> {

    private final ProductRepository productRepository;

    DeleteProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Void> execute(UUID uuid) {
        Optional<Product> product = productRepository.findById(uuid);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().build();
    }
}
