package com.api.bonobo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.api.bonobo.exception.InvalidFieldsException;
import com.api.bonobo.model.Category;
import com.api.bonobo.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryServiceTest {
    @InjectMocks
    CategoryService categoryService;
    @Mock
    CategoryRepository categoryRepository;

    @Test
    void shouldCreateCategory() {
        Assertions.assertThatCode(() -> categoryService.create(createCategory())).doesNotThrowAnyException();
    }

    @Test
    void shouldNotCreateCategoryWithoutName() {
        Category category = createCategory();
        category.setName("");

        Assertions.assertThatThrownBy(() -> categoryService.create(category)).isInstanceOf(InvalidFieldsException.class);
    }

    private Category createCategory() {
       return Category.builder()
               .id(1L)
               .name("Category")
               .build();
    }
}
