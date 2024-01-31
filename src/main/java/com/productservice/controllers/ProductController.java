package com.productservice.controllers;

import com.productservice.exceptions.ProductDoesNotExistsException;
import com.productservice.models.Product;
import com.productservice.services.EComProductService;
import com.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Product APIs supported:
 *  1. Get all products (GET)
 *  2. Get a single product (GET)
 *  3. Add a new product (POST)
 *  4. Replace a product (PUT)
 *  5. Update a product (PATCH)
 *  6. Remove a product (DELETE)
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private EComProductService productService;

    @Autowired
    public ProductController(@Qualifier("productService") EComProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        ResponseEntity<List<Product>> response = new ResponseEntity<>(
                productService.getAllProducts(), HttpStatus.OK
        );

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long id) throws ProductDoesNotExistsException {
        ResponseEntity<Product> response =
                new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
        return response;
    }

    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        ResponseEntity<Product> response =
                new ResponseEntity<>(productService.addProduct(product), HttpStatus.OK);

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product product)
            throws ProductDoesNotExistsException {
        ResponseEntity<Product> response =
                new ResponseEntity<>(productService.replaceProduct(id, product), HttpStatus.OK);

        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") Long id, @RequestBody Product product)
                throws ProductDoesNotExistsException {
        ResponseEntity<Product> response =
                new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable(name = "id") Long id) throws ProductDoesNotExistsException {
        productService.removeProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(ProductDoesNotExistsException.class)
    public ResponseEntity<String> handleProductDoesNotExistsException(ProductDoesNotExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
