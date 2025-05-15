package com.example.amazon.Product.CommandHandlers;

import com.example.amazon.Category.CategoryRepository;
import com.example.amazon.Command;
import com.example.amazon.Product.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CreateProductCommandHandler implements Command<ProductRequest, ProductDTO> {
    private final Logger logger = LoggerFactory.getLogger(CreateProductCommandHandler.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CreateProductCommandHandler(
            ProductRepository productRepository,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(ProductRequest request) {
        logger.info("Create product command handler " + request);

        Product product = ProductValidator.execute(request, categoryRepository.findAll());
        productRepository.save(product);

        return ResponseEntity.ok(new ProductDTO(product));
    }
}
