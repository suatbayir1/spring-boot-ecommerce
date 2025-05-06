package com.example.amazon.Product;

import com.example.amazon.Product.CommandHandlers.DeleteProductCommandHandler;
import com.example.amazon.Product.QueryHandlers.GetProductByIdQueryHandler;
import com.example.amazon.Product.QueryHandlers.GetProductsQueryHandler;
import lombok.Getter;
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

    public ProductController(
            GetProductsQueryHandler getProductsQueryHandler,
            GetProductByIdQueryHandler getProductByIdQueryHandler,
            DeleteProductCommandHandler deleteProductCommandHandler
    ) {
        this.getProductsQueryHandler = getProductsQueryHandler;
        this.getProductByIdQueryHandler = getProductByIdQueryHandler;
        this.deleteProductCommandHandler = deleteProductCommandHandler;
    }

    @GetMapping()
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

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteProductById(@PathVariable UUID uuid) {
        return deleteProductCommandHandler.execute(uuid);
    }
}
