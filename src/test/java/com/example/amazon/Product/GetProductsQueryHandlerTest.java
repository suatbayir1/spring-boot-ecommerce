package com.example.amazon.Product;

import com.example.amazon.AmazonApplicationTests;
import com.example.amazon.Category.Category;
import com.example.amazon.Product.QueryHandlers.GetProductsQueryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AmazonApplicationTests.class)
public class GetProductsQueryHandlerTest {

    @Mock
    private ProductRepository productRepository;

    private GetProductsQueryHandler getProductsQueryHandler;

    @BeforeEach
    void setup() {
        getProductsQueryHandler = new GetProductsQueryHandler(productRepository);
    }

    @Test
    void testGetProductsQueryHandler_returnsEmptyList() {
        when(productRepository
                .findByNameOrDescriptionAndRegionAndCategory(null, null, null, null))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<ProductDTO>> responseEntity = getProductsQueryHandler
                .execute(new GetProductsQuery(null, null, null, null));

        assertEquals(responseEntity.getBody(), Collections.emptyList());
    }

    @Test
    void testGetProductsQueryHandler_returnsList() {
        when(productRepository
                .findByNameOrDescriptionAndRegionAndCategory(any(), any(), any(), any()))
                .thenReturn(getProducts());

        ResponseEntity<List<ProductDTO>> responseEntity = getProductsQueryHandler.execute(
                new GetProductsQuery(null, null, null, null));

        List<ProductDTO> actualList = responseEntity.getBody();
        assertEquals(2, actualList.size());
    }

    @Test
    void testDefineSort_returnsUnsorted() {
        Sort sort = getProductsQueryHandler.defineSort(null);
        assertEquals(Sort.unsorted(), sort);
    }

    @Test
    void testDefineSort_returnsSorted() {
        Sort sort = getProductsQueryHandler.defineSort(ProductSortBy.name);
        assertEquals(Sort.by(Sort.Direction.ASC, "name"), sort);
    }

    private List<Product> getProducts() {
        Product product1 = new Product();
        product1.setUuid(UUID.randomUUID());
        product1.setCategory(new Category("ELECTRONICS"));

        Product product2 = new Product();
        product2.setUuid(UUID.randomUUID());
        product2.setCategory(new Category("ELECTRONICS"));

        return Arrays.asList(product1, product2);
    }
}
