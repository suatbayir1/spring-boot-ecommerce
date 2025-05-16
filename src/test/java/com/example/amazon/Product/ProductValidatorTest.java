package com.example.amazon.Product;

import com.example.amazon.AmazonApplicationTests;
import com.example.amazon.Category.Category;
import com.example.amazon.Exceptions.ErrorMessage;
import com.example.amazon.Exceptions.InvalidProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = AmazonApplicationTests.class)
public class ProductValidatorTest {
    @Mock
    private ProductValidator productValidator;

    @BeforeEach
    void setup() {
        this.productValidator = new ProductValidator();
    }

    private ProductRequest getValidProductRequest() {
        return new ProductRequest(
                "testName",
                "testDescription",
                "testManufacturer",
                199.99,
                "ELECTRONICS",
                "TR"
        );
    }

    private List<Category> getCategories() {
        return Arrays.asList(
                new Category("ELECTRONICS"),
                new Category("FOOD")
        );
    }

    @Test
    void testNameIsEmpty_throwsInvalidProductException() {
        ProductRequest productRequestNull = getValidProductRequest();
        productRequestNull.setName(null);
        InvalidProductException exceptionNull = assertThrows(InvalidProductException.class,
                () -> productValidator.execute(productRequestNull, getCategories()));
        assertEquals(exceptionNull.getSimpleResponse().getMessage(), ErrorMessage.PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage());

        ProductRequest productRequestEmpty = getValidProductRequest();
        productRequestEmpty.setName("");
        InvalidProductException exceptionEmpty = assertThrows(InvalidProductException.class,
                () -> productValidator.execute(productRequestEmpty, getCategories()));
        assertEquals(exceptionEmpty.getSimpleResponse().getMessage(), ErrorMessage.PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage());
    }

    @Test
    void testPriceIsNegative_throwsInvalidProductException() {
        ProductRequest productRequest = getValidProductRequest();
        productRequest.setPrice(-1.99);
        InvalidProductException exception = assertThrows(InvalidProductException.class,
                () -> productValidator.execute(productRequest, getCategories()));
        assertEquals(exception.getSimpleResponse().getMessage(), ErrorMessage.PRODUCT_PRICE_CANNOT_BE_NEGATIVE.getMessage());
    }

    @Test
    void testCategoryNotAvailable_throwsInvalidProductException() {
        ProductRequest productRequest = getValidProductRequest();
        productRequest.setCategory("Not available");
        InvalidProductException exception = assertThrows(InvalidProductException.class,
                () -> productValidator.execute(productRequest, getCategories()));
        assertEquals(exception.getSimpleResponse().getMessage(), ErrorMessage.PRODUCT_CATEGORY_IS_NOT_AVAILABLE.getMessage());
    }

    @Test
    void testCategoriesEmpty_throwsInvalidProductException() {
        ProductRequest productRequest = getValidProductRequest();
        InvalidProductException exception = assertThrows(InvalidProductException.class,
                () -> productValidator.execute(productRequest, Collections.emptyList()));
        assertEquals(exception.getSimpleResponse().getMessage(), ErrorMessage.PRODUCT_CATEGORY_IS_NOT_AVAILABLE.getMessage());
    }

    @Test
    void testRegionNotAvailable_throwsInvalidProductException() {
        ProductRequest productRequest = getValidProductRequest();
        productRequest.setRegion("not available");
        InvalidProductException exception = assertThrows(InvalidProductException.class, () ->
                productValidator.execute(productRequest, getCategories()));
        assertEquals(exception.getSimpleResponse().getMessage(), ErrorMessage.PRODUCT_REGION_IS_NOT_AVAILABLE.getMessage());
    }
}
