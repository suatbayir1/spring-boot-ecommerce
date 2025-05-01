package com.example.amazon.Category;

import com.example.amazon.AmazonApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AmazonApplicationTests.class)
public class GetCategoryQueryHandlerTest {
    @Mock
    private CategoryRepository categoryRepository;

    private GetCategoryQueryHandler getCategoryQueryHandler;

    @BeforeEach
    void setup() {
        getCategoryQueryHandler = new GetCategoryQueryHandler(categoryRepository);
    }

    @Test
    void categoryQueryHandler_returnsList() {
        List<Category> categoryList = Arrays.asList(
                new Category("Electronics"),
                new Category("Bathroom"),
                new Category("Automobile")
        );

        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<String> expected = Arrays.asList("Electronics", "Bathroom", "Automobile");
        ResponseEntity<List<String>> actual = getCategoryQueryHandler.execute(null);

        assertEquals(ResponseEntity.ok(expected), actual);
    }

    @Test
    void categoryQueryHandler_returnsEmptyList() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<String>> actual = getCategoryQueryHandler.execute(null);
        assertEquals(ResponseEntity.ok(Collections.emptyList()), actual);
    }
}
