package com.example.amazon.Product.CommandHandlers;

import com.example.amazon.Category.CategoryRepository;
import com.example.amazon.Command;
import com.example.amazon.Exceptions.ProductNotFoundException;
import com.example.amazon.Product.Product;
import com.example.amazon.Product.ProductDTO;
import com.example.amazon.Product.ProductRepository;
import com.example.amazon.Product.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProductCommandHandler implements Command<UpdateProductCommand, ProductDTO> {
    private final Logger logger = LoggerFactory.getLogger(UpdateProductCommandHandler.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductValidator productValidator;

    public UpdateProductCommandHandler(ProductRepository productRepository, CategoryRepository categoryRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productValidator = productValidator;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {
        logger.info("Update product command handler " + command.getUuid());

        Product product = productRepository
                .findById(command.getUuid())
                .orElseThrow(ProductNotFoundException::new);

        Product validatedProduct = productValidator.execute(command.getRequest(), categoryRepository.findAll());
        validatedProduct.setUuid(product.getUuid());
        validatedProduct.setUpdatedAt(product.getUpdatedAt());
        productRepository.save(validatedProduct);

        return ResponseEntity.ok(new ProductDTO(validatedProduct));
    }
}
