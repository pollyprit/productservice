package com.productservice.services;

import com.productservice.exceptions.CategoryDoesNotExistsException;
import com.productservice.exceptions.ProductDoesNotExistsException;
import com.productservice.models.Category;
import com.productservice.models.Product;
import com.productservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) throws CategoryDoesNotExistsException {
        Optional<Category> catOptional = categoryRepository.findById(id);

        if (catOptional.isEmpty())
            throw new CategoryDoesNotExistsException("Category with id " + id + " not found");

        return catOptional.get();
    }
}
