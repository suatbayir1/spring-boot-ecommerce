package com.example.amazon.Product.QueryHandlers;

import com.example.amazon.Product.*;
import com.example.amazon.Query;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetProductsQueryHandler implements Query<GetProductsQuery, List<ProductDTO>> {
    private final ProductRepository productRepository;

    public GetProductsQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(GetProductsQuery query) {
        Sort productSort = defineSort(query.getProductSortBy());

        List<Product> products = productRepository
                .findByNameOrDescriptionAndRegionAndCategory(
                        query.getNameOrDescription(),
                        query.getRegion(),
                        query.getCategory(),
                        productSort
                );

        List<ProductDTO> productDTOS = products.stream()
                .map(ProductDTO::new)
                .toList();

        return ResponseEntity.ok(productDTOS);
    }

    public Sort defineSort(ProductSortBy productSortBy) {
        if (productSortBy == null) {
            return Sort.unsorted();
        }

        ProductSortBy sortBy = ProductSortBy.valueOf(productSortBy.getValue());
        return Sort.by(String.valueOf(sortBy));
    }
}
