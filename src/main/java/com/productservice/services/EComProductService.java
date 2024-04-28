package com.productservice.services;

import com.productservice.exceptions.ProductDoesNotExistsException;
import com.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EComProductService {
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    public Product getProduct(Long id) throws ProductDoesNotExistsException;
    public Product addProduct(Product product);
    public void removeProduct(Long id) throws ProductDoesNotExistsException;
    public Product updateProduct(Long id, Product product) throws ProductDoesNotExistsException;
    public Product replaceProduct(Long id, Product product) throws ProductDoesNotExistsException;
}
