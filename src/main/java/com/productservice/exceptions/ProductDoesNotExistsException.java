package com.productservice.exceptions;

public class ProductDoesNotExistsException extends Exception {
    public ProductDoesNotExistsException(String message) {
        super(message);
    }
}
