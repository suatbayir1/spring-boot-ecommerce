package com.example.amazon.Product;

import com.example.amazon.AmazonApplicationTests;
import com.example.amazon.Category.Category;
import com.example.amazon.Exceptions.ProductNotFoundException;
import com.example.amazon.Product.QueryHandlers.GetProductByIdQueryHandler;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AmazonApplicationTests.class)
public class GetProductByIdQueryHandlerTest {
    @Mock
    private ProductRepository productRepository;

    private GetProductByIdQueryHandler getProductByIdQueryHandler;

    @BeforeEach
    void setup() {
        getProductByIdQueryHandler = new GetProductByIdQueryHandler(productRepository);
    }

    @Test
    void getProductByIdQueryHandler_returnsSuccess() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setUuid(uuid);
        product.setCategory(new Category("Electronics"));

        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));

        ResponseEntity<ProductDTO> productDTOResponseEntity = getProductByIdQueryHandler.execute(uuid);
        verify(productRepository).findById(uuid);

        assertEquals(productDTOResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(productDTOResponseEntity.getBody(), new ProductDTO(product));
    }

    @Test
    void getProductByIdQueryHandler_throwsProductNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setUuid(uuid);
        product.setCategory(new Category("Electronics"));

        when(productRepository.findById(uuid)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> getProductByIdQueryHandler.execute(uuid));

        assertEquals("Product not found", exception.getSimpleResponse().getMessage());
    }
}
