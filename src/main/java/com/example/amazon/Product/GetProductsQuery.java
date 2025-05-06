package com.example.amazon.Product;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetProductsQuery {
    private Region region;
    private String category;
    private String nameOrDescription;
    private ProductSortBy productSortBy;

    public GetProductsQuery(Region region, String category, String nameOrDescription, ProductSortBy productSortBy) {
        this.region = region;
        this.category = category;
        this.nameOrDescription = nameOrDescription;
        this.productSortBy = productSortBy;
    }
}
