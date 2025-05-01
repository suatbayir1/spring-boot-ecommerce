package com.example.amazon.Category;

import com.example.amazon.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetCategoryQueryHandler implements Query<Void, List<String>> {
    private final CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(GetCategoryQueryHandler.class);

    public GetCategoryQueryHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<List<String>> execute(Void input) {
        logger.info("Executing {} GetCategoryQueryHandler", getClass().getSimpleName());

        return ResponseEntity.ok(categoryRepository
                .findAll()
                .stream()
                .map(Category::getValue)
                .collect(Collectors.toList())
        );
    }
}
