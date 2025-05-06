package com.example.amazon.Configuration;

public enum CacheName {
    PRODUCTS("products");

    private final String value;

    CacheName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
