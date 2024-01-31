package com.productservice.services;

import com.productservice.exceptions.ProductDoesNotExistsException;
import com.productservice.models.Category;
import com.productservice.models.Product;
import com.productservice.repositories.CategoryRepository;
import com.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements EComProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) throws ProductDoesNotExistsException {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty())
            throw new ProductDoesNotExistsException("Product with id " + id + " not found");

        return productOptional.get();
    }

    public Product addProduct(Product product) {
        Optional<Category> optionalCategory
                = categoryRepository.findByName(product.getCategory().getName());

        if (optionalCategory.isEmpty()) {
            //Category category = categoryRepository.save(product.getCategory());
            //product.setCategory(category);
        }
        else {
            product.setCategory(optionalCategory.get());
        }
        return productRepository.save(product);
    }

    public void removeProduct(Long id) throws ProductDoesNotExistsException {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty())
            throw new ProductDoesNotExistsException("Product with id " + id + " not found");

        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product product) throws ProductDoesNotExistsException {
        Product oldProduct = getProduct(id);

        if (product.getTitle() != null)
            oldProduct.setTitle(product.getTitle());
        if (product.getPrice() != null)
            oldProduct.setPrice(product.getPrice());
        if (product.getDescription() != null)
            oldProduct.setDescription(product.getDescription());
        if (product.getImageUrl() != null)
            oldProduct.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null)
            oldProduct.setCategory(product.getCategory());

        return productRepository.save(oldProduct);
    }

    public Product replaceProduct(Long id, Product product) {
        // Try to replace an existing product
        try {
            Product oldProduct = getProduct(id);

            oldProduct.setTitle(product.getTitle());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setImageUrl(product.getImageUrl());
            oldProduct.setCategory(product.getCategory());

            return productRepository.save(oldProduct);
        }
        catch (ProductDoesNotExistsException exp) {
            // ignore it, and add new product
        }
        return addProduct(product);
    }
}
