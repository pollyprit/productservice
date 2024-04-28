package com.productservice.controllers;

import com.productservice.exceptions.CategoryDoesNotExistsException;
import com.productservice.models.Category;
import com.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Category APIs supported:
 *  1. Get all categories (GET /categories)
 *  2. Get a single category (GET /categories/{id})
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    ResponseEntity<List<Category>> getAllCategories() {
        ResponseEntity<List<Category>> response = new ResponseEntity<>(
                categoryService.getAllCategories(), HttpStatus.OK
        );

        return response;
    }

    @GetMapping("/{id}")
    ResponseEntity<Category> getSingleCategory(@PathVariable(name="id") Long id) throws CategoryDoesNotExistsException {
        ResponseEntity<Category> response = new ResponseEntity<>(
                categoryService.getCategory(id), HttpStatus.OK);

        return response;
    }

    @ExceptionHandler(CategoryDoesNotExistsException.class)
    public ResponseEntity<String> handleCategoryDoesNotExistsException(CategoryDoesNotExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
