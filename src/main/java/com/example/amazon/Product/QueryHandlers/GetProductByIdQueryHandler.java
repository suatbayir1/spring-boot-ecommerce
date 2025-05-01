package com.example.amazon.Product.QueryHandlers;

import com.example.amazon.Category.GetCategoryQueryHandler;
import com.example.amazon.Exceptions.ProductNotFoundException;
import com.example.amazon.Product.Product;
import com.example.amazon.Product.ProductDTO;
import com.example.amazon.Product.ProductRepository;
import com.example.amazon.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetProductByIdQueryHandler implements Query<UUID, ProductDTO> {
    private final Logger logger = LoggerFactory.getLogger(GetProductByIdQueryHandler.class);

    private final ProductRepository productRepository;

    public GetProductByIdQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UUID uuid) {
        logger.info("Get product by id query handler, uuid: " + uuid);
        Optional<Product> product = productRepository.findById(uuid);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return ResponseEntity.ok(new ProductDTO(product.get()));
    }
}
