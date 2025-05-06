package com.example.amazon.Product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:nameOrDescription is NULL OR p.name like %:nameOrDescription% OR p.description like %:nameOrDescription%) and " +
            "(p.region = :region) and " +
            "(:category is NULL OR p.category.value = :category)")
    List<Product> findByNameOrDescriptionAndRegionAndCategory(
            String nameOrDescription,
            Region region,
            String category,
            Sort sort
    );
}
