package com.example.amazon.Exceptions;

public enum ErrorMessage {
    PRODUCT_NOT_FOUND("Product not found");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
