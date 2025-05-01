package com.example.amazon.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final GetCategoryQueryHandler getCategoryQueryHandler;

    public CategoryController(GetCategoryQueryHandler getCategoryQueryHandler) {
        this.getCategoryQueryHandler = getCategoryQueryHandler;
    }

    @GetMapping()
    public ResponseEntity<List<String>> getCategories() {
        return getCategoryQueryHandler.execute(null);
    }
}
