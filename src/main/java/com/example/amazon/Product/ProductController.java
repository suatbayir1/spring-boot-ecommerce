package com.example.amazon.Product;

import com.example.amazon.Product.CommandHandlers.DeleteProductCommandHandler;
import com.example.amazon.Product.QueryHandlers.GetProductByIdQueryHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final GetProductByIdQueryHandler getProductByIdQueryHandler;
    private final DeleteProductCommandHandler deleteProductCommandHandler;

    public ProductController(
            GetProductByIdQueryHandler getProductByIdQueryHandler,
            DeleteProductCommandHandler deleteProductCommandHandler
    ) {
        this.getProductByIdQueryHandler = getProductByIdQueryHandler;
        this.deleteProductCommandHandler = deleteProductCommandHandler;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID uuid) {
        return getProductByIdQueryHandler.execute(uuid);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteProductById(@PathVariable UUID uuid) {
        return deleteProductCommandHandler.execute(uuid);
    }
}
