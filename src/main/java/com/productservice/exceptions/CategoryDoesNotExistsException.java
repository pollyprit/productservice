package com.productservice.exceptions;

public class CategoryDoesNotExistsException extends Exception {
    public CategoryDoesNotExistsException(String message) {
        super(message);
    }
}
