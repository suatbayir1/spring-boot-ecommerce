package com.example.amazon.Product.CommandHandlers;

import com.example.amazon.Command;
import com.example.amazon.Exceptions.ProductNotFoundException;
import com.example.amazon.Product.Product;
import com.example.amazon.Product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteProductCommandHandler implements Command<UUID, Void> {
    private final Logger logger = LoggerFactory.getLogger(DeleteProductCommandHandler.class);

    private final ProductRepository productRepository;

    public DeleteProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Void> execute(UUID uuid) {
        logger.info("Delete product command hanlder, uuid: " + uuid);
        Optional<Product> product = productRepository.findById(uuid);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().build();
    }
}
