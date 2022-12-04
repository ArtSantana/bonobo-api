package com.api.bonobo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bonobo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
