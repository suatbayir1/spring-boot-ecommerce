package com.example.amazon.Product;

import com.example.amazon.AmazonApplicationTests;
import com.example.amazon.Exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AmazonApplicationTests.class)
public class DeleteProductCommandHandlerTest {
    @Mock
    private ProductRepository productRepository;

    private DeleteProductCommandHandler deleteProductCommandHandler;

    @BeforeEach
    void setup() {
        deleteProductCommandHandler = new DeleteProductCommandHandler(productRepository);
    }

    @Test
    void deleteProductCommandHandler_returnsSuccess() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setUuid(uuid);

        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));
        ResponseEntity responseEntity = deleteProductCommandHandler.execute(uuid);
        verify(productRepository).delete(product);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteProductCommandHandler_throwsProductNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setUuid(uuid);

        when(productRepository.findById(uuid)).thenReturn(Optional.empty());
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> deleteProductCommandHandler.execute(uuid));
        assertEquals("Product not found", exception.getSimpleResponse().getMessage());
    }
}
