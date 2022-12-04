package com.api.bonobo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.bonobo.exception.InvalidFieldsException;
import com.api.bonobo.exception.NotFoundException;
import com.api.bonobo.model.Category;
import com.api.bonobo.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            String message = "Category with id %s not found";
            throw new NotFoundException(String.format(message, id));
        }

        return category.get();
    }

    public void create(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new InvalidFieldsException(Collections.singletonList("Category must have a name!"));
        }
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new IllegalStateException("Could not create category");
        }
    }
}