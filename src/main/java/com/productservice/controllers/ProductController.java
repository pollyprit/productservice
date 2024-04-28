package com.productservice.controllers;

import com.productservice.common.AuthenticationCommon;
import com.productservice.dtos.RoleDto;
import com.productservice.dtos.UserDto;
import com.productservice.exceptions.ProductDoesNotExistsException;
import com.productservice.models.Product;
import com.productservice.services.EComProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Product APIs supported:
 *  1. Get all products (GET /products?pageSize=3&pageNumber=0&sortBy=price&sortOrder=asc)
 *  2. Get a single product (GET /products/{id})
 *  3. Add a new product (POST /products)
 *  4. Replace a product (PUT /products/{id})
 *  5. Update a product (PATCH /products/{id})
 *  6. Remove a product (DELETE /products/{id})
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private EComProductService productService;
    private AuthenticationCommon authenticationCommon;

    @Autowired
    public ProductController(@Qualifier("productService") EComProductService productService,
                             AuthenticationCommon authenticationCommon) {
        this.productService = productService;
        this.authenticationCommon = authenticationCommon;
    }

    @GetMapping()  // /products?pageSize=3&pageNumber=0&sortBy=price&sortOrder=asc
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam("pageNumber") int pageNumber,
                                                        @RequestParam("pageSize")int pageSize,
                                                        @RequestParam("sortBy") String sortBy,
                                                        @RequestParam("sortOrder") String sortOrder) {

        // Authentication is now managed by the framework, so no need to do explicitly.
        // Authenticate the token from user-service
        /*UserDto userDto = authenticationCommon.validateToken(token);

        if (userDto == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        boolean admin = true;

        // Check role based access control
        for (RoleDto role : userDto.getRoles()) {
            if (role.getName().equals("ADMIN")) {
                admin = true;
                break;
            }
        }
        if (!admin)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);*/

        ResponseEntity<Page<Product>> response = new ResponseEntity<>(
                productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK
        );

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long id) throws ProductDoesNotExistsException {
        ResponseEntity<Product> response =
                new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
        return response;
    }

    @PostMapping()
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
