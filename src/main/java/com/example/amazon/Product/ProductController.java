package com.example.amazon.Product;

import com.example.amazon.Product.CommandHandlers.CreateProductCommandHandler;
import com.example.amazon.Product.CommandHandlers.DeleteProductCommandHandler;
import com.example.amazon.Product.CommandHandlers.UpdateProductCommand;
import com.example.amazon.Product.CommandHandlers.UpdateProductCommandHandler;
import com.example.amazon.Product.QueryHandlers.GetProductByIdQueryHandler;
import com.example.amazon.Product.QueryHandlers.GetProductsQueryHandler;
import lombok.Getter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final GetProductsQueryHandler getProductsQueryHandler;
    private final GetProductByIdQueryHandler getProductByIdQueryHandler;
    private final DeleteProductCommandHandler deleteProductCommandHandler;
    private final CreateProductCommandHandler createProductCommandHandler;
    private final UpdateProductCommandHandler updateProductCommandHandler;

    public ProductController(
            GetProductsQueryHandler getProductsQueryHandler,
            GetProductByIdQueryHandler getProductByIdQueryHandler,
            DeleteProductCommandHandler deleteProductCommandHandler,
            CreateProductCommandHandler createProductCommandHandler,
            UpdateProductCommandHandler updateProductCommandHandler
    ) {
        this.getProductsQueryHandler = getProductsQueryHandler;
        this.getProductByIdQueryHandler = getProductByIdQueryHandler;
        this.deleteProductCommandHandler = deleteProductCommandHandler;
        this.createProductCommandHandler = createProductCommandHandler;
        this.updateProductCommandHandler = updateProductCommandHandler;
    }

    @GetMapping()
    @Cacheable("products")
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestHeader(value = "region", defaultValue = "US") String region,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String nameOrDescription,
            @RequestParam(required = false) String orderBy
    ) {
        return getProductsQueryHandler.execute(new GetProductsQuery(
                Region.valueOf(region),
                category,
                nameOrDescription,
                ProductSortBy.fromValue(orderBy)
        ));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID uuid) {
        return getProductByIdQueryHandler.execute(uuid);
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequest request) {
        return createProductCommandHandler.execute(request);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID uuid, @RequestBody ProductRequest request) {
        return updateProductCommandHandler.execute(new UpdateProductCommand(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteProductById(@PathVariable UUID uuid) {
        return deleteProductCommandHandler.execute(uuid);
    }
}
