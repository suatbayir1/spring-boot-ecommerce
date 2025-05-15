package com.example.amazon.Product;

import com.example.amazon.Category.Category;
import com.example.amazon.Exceptions.ErrorMessage;
import com.example.amazon.Exceptions.InvalidProductException;
import com.example.amazon.Exceptions.SimpleResponse;
import io.micrometer.common.util.StringUtils;

import java.util.List;

public class ProductValidator {
    public static Product execute(ProductRequest request, List<Category> availableCategories) {
        if (nameIsEmpty(request.getName())) {
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage()), request);
        }

        if (priceIsNegative(request.getPrice())) {
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_PRICE_CANNOT_BE_NEGATIVE.getMessage()), request);
        }

        if (categoryNotAvailable(request.getCategory(), availableCategories)) {
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_CATEGORY_IS_NOT_AVAILABLE.getMessage()), request);
        }

        if (regionNotAvailable(request.getRegion())) {
            throw new InvalidProductException(new SimpleResponse(ErrorMessage.PRODUCT_REGION_IS_NOT_AVAILABLE.getMessage()), request);
        }

        return new Product(request);
    }

    private static boolean nameIsEmpty(String name) {
        return StringUtils.isEmpty(name);
    }

    private static boolean priceIsNegative(Double price) {
        return price != null && price < 0.00;
    }

    private static boolean categoryNotAvailable(String category, List<Category> availableCategories) {
        return !availableCategories.contains(new Category(category));
    }

    private static boolean regionNotAvailable(String candidateRegion) {
        for (Region region: Region.values()) {
            if (region.name().equals(candidateRegion)) {
                return false;
            }
        }

        return true;
    }
}
