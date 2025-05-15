package com.example.amazon.Exceptions;

public enum ErrorMessage {
    PRODUCT_NOT_FOUND("Product not found"),
    PRODUCT_NAME_CANNOT_BE_EMPTY("Product name cannot be empty"),
    PRODUCT_PRICE_CANNOT_BE_NEGATIVE("Product price cannot be negative"),
    PRODUCT_CATEGORY_IS_NOT_AVAILABLE("Product category is not avaiable"),
    PRODUCT_REGION_IS_NOT_AVAILABLE("Product region is not available");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
